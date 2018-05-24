package br.edu.ifspsaocarlos.sdm.btchatsdm;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnClickListener, DialogInterface.OnCancelListener {

    //Constantes
    private final String SERVICO_BLUETOOTH = "BLUETOOTH_CHAT_SDM";
    private final UUID UUID_SERVICO_BLUETOOTH = UUID.fromString("bf4edea9-4636-4c08-829c-a80b491107af");

    private final int TEMPO_DESCOBERTA_SERVICO_BLUETOOTH = 30;

    private final int ATIVA_BLUETOOTH = 0;
    private final int ATIVA_DESCOBERTA = 1;

    private final int MENSAGEM_TEXTO = 0;
    private final int MENSAGEM_DESCONEXAO = 2;

    private ThreadServidor threadServidor;
    private ThreadCliente threadCliente;
    private ThreadComunicacao threadComunicacao;

    private BluetoothAdapter bluetoothAdapter;
    private List<BluetoothDevice> bluetoothDeviceList;

    private EventosBTBroadcastReceiver eventosBTBroadcastReceiver;

    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    private ArrayAdapter<String> historicoAdapter;

    private TelaPrincipalHandler telaPrincipalHandler;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        historicoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        ListView listView = findViewById(R.id.lv_nova_mensagem);
        listView.setAdapter(historicoAdapter);

        telaPrincipalHandler = new TelaPrincipalHandler();

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter != null) {
            if(!bluetoothAdapter.isEnabled()) {
                Intent ativaBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(ativaBTIntent, ATIVA_BLUETOOTH);
            }
        } else {
            Toast.makeText(this, "Adaptador Bluetooth não disponível", Toast.LENGTH_LONG).show();
            finish();
        }

        bluetoothDeviceList = new ArrayList<>();
        eventosBTBroadcastReceiver = new EventosBTBroadcastReceiver();
        registerReceiver(eventosBTBroadcastReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        registerReceiver(eventosBTBroadcastReceiver, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_modo_aplicativo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        boolean handled = false;

        switch (item.getItemId()) {
            case R.id.item_menu_modo_servidor:
                Toast.makeText(this, "Configurando aplicativo em modo servidor", Toast.LENGTH_SHORT).show();

                Intent descobertaIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                descobertaIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, TEMPO_DESCOBERTA_SERVICO_BLUETOOTH);
                startActivityForResult(descobertaIntent, ATIVA_DESCOBERTA);

                handled = true;
                break;

            case R.id.item_menu_modo_cliente:
                bluetoothDeviceList.clear();
                bluetoothAdapter.startDiscovery();

                exibirAguardeDialog("Procurando dispositivos...", 0);

                Toast.makeText(this, "Configurando aplicativo em modo cliente", Toast.LENGTH_SHORT).show();
                handled = true;
                break;
        }

        return handled;
    }

    private void exibirAguardeDialog(String mensagem, int tempo) {
        progressDialog = ProgressDialog.show(this, "Aguarde", mensagem, true, true, this);
        progressDialog.show();
        if(tempo > 0) {
            telaPrincipalHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(threadComunicacao == null) {
                        progressDialog.cancel();
                    }
                }
            }, tempo*1000);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ATIVA_BLUETOOTH:
                if(resultCode != RESULT_OK) {
                    Toast.makeText(this, "Bluetooth necessário", Toast.LENGTH_LONG);
                    finish();
                }
                break;

            case ATIVA_DESCOBERTA:
                if(resultCode == TEMPO_DESCOBERTA_SERVICO_BLUETOOTH) {
                    iniciaThreadServidor();
                } else {
                    Toast.makeText(this, "Visibilidade bluetooth necessária", Toast.LENGTH_LONG);
                    finish();
                }
                break;
        }
    }

    private void iniciaThreadServidor() {
        paraThreadsFilhas();

        exibirAguardeDialog("Aguardando conexões...", TEMPO_DESCOBERTA_SERVICO_BLUETOOTH);

        threadServidor = new ThreadServidor();
        threadServidor.iniciar();
    }

    private void iniciaThreadCliente(int btDeviceIndex) {
        BluetoothDevice btDevice = bluetoothDeviceList.get(btDeviceIndex);

        threadCliente = new ThreadCliente();
        threadCliente.iniciar(btDevice);
    }

    private void paraThreadsFilhas() {
        if(threadServidor != null) {
            threadServidor.parar();
            threadServidor = null;
        }

        if(threadCliente != null) {
            threadCliente.parar();
            threadCliente = null;
        }

        if(threadComunicacao != null) {
            threadComunicacao.parar();
            threadComunicacao = null;
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        dialog.cancel();
        bluetoothAdapter.cancelDiscovery();
        paraThreadsFilhas();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        iniciaThreadCliente(which);
        bluetoothAdapter.cancelDiscovery();
        dialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        paraThreadsFilhas();
        unregisterReceiver(eventosBTBroadcastReceiver);
    }

    private void exibirDispositivosEncontrados() {
        progressDialog.dismiss();

        String[] btDevices = new String[bluetoothDeviceList.size()];

        for (int i = 0 ; i < bluetoothDeviceList.size() ; i++) {
            btDevices[i] = bluetoothDeviceList.get(i).getName();
        }

        AlertDialog btDevicesDialog = new AlertDialog.Builder(this)
                .setTitle("Dispositivos")
                .setSingleChoiceItems(btDevices, -1, this)
                .create();

        btDevicesDialog.show();
    }

    public void enviarMensagem(View view) {
        if(view.getId() == R.id.bt_enviar) {
            EditText mensagemEditText = findViewById(R.id.et_mensagem);
            String mensagem = mensagemEditText.getText().toString();
            mensagemEditText.setText("");

            try {
                outputStream.writeUTF(mensagem);
                historicoAdapter.add("Eu: " + mensagem);
                historicoAdapter.notifyDataSetChanged();
            } catch (IOException e) {
                telaPrincipalHandler.obtainMessage(MENSAGEM_DESCONEXAO, e.getMessage() + "[ENVIAR_MENSAGEM]").sendToTarget();
                e.printStackTrace();
            }
        }
    }

    private class TelaPrincipalHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case MENSAGEM_DESCONEXAO:
                    Toast.makeText(MainActivity.this, "Desconectado: " + msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case MENSAGEM_TEXTO:
                    historicoAdapter.add(msg.obj.toString());
                    historicoAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }


    private class EventosBTBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(BluetoothDevice.ACTION_FOUND.equals(intent.getAction())) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                bluetoothDeviceList.add(device);

            } else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(intent.getAction())) {
                exibirDispositivosEncontrados();
            }
        }
    }


    private class ThreadServidor extends Thread {
        private BluetoothServerSocket serverSocket;
        private BluetoothSocket socket;

        @Override
        public void run() {
            try {
                serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(SERVICO_BLUETOOTH, UUID_SERVICO_BLUETOOTH);
                socket = serverSocket.accept();
                trataSocket(socket);
            } catch (IOException e) {
                telaPrincipalHandler.obtainMessage(MENSAGEM_DESCONEXAO, e.getMessage() + "[SERVIDOR]").sendToTarget();
                e.printStackTrace();
            }
        }

        public void iniciar() {
            start();
        }

        public void parar() {
            try {
                serverSocket.close();
            } catch (IOException e) {
                telaPrincipalHandler.obtainMessage(MENSAGEM_DESCONEXAO, e.getMessage() + "[SERVIDOR]").sendToTarget();
                e.printStackTrace();
            }
        }
    }

    private class ThreadCliente extends Thread {
        private BluetoothDevice bluetoothDevice;
        private BluetoothSocket socket;

        public void iniciar(BluetoothDevice btDevice) {
            this.bluetoothDevice = btDevice;
            start();
        }

        private void parar() {
            try {
                socket.close();
            } catch (IOException e) {
                telaPrincipalHandler.obtainMessage(MENSAGEM_DESCONEXAO, e.getMessage() + "[CLIENTE]").sendToTarget();
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                socket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(UUID_SERVICO_BLUETOOTH);
                socket.connect();
                trataSocket(socket);
            } catch (IOException e) {
                telaPrincipalHandler.obtainMessage(MENSAGEM_DESCONEXAO, e.getMessage() + "[CLIENTE]").sendToTarget();
                e.printStackTrace();
            }
        }
    }

    private void trataSocket(BluetoothSocket socket) {
        progressDialog.dismiss();
        threadComunicacao = new ThreadComunicacao();
        threadComunicacao.iniciar(socket);
    }

    private class ThreadComunicacao extends Thread {
        private BluetoothSocket bluetoothSocket;

        public void iniciar(BluetoothSocket bluetoothSocket) {
            this.bluetoothSocket = bluetoothSocket;
            start();
        }

        public void parar() {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                telaPrincipalHandler.obtainMessage(MENSAGEM_DESCONEXAO, e.getMessage() + "[THREAD_COMUNICACAO]").sendToTarget();
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                inputStream = new DataInputStream(bluetoothSocket.getInputStream());
                outputStream = new DataOutputStream(bluetoothSocket.getOutputStream());
                String mensagem;
                String nomeDispositivoRemoto = bluetoothSocket.getRemoteDevice().getName();
                while (true) {
                    mensagem = inputStream.readUTF();
                    telaPrincipalHandler.obtainMessage(MENSAGEM_TEXTO, mensagem).sendToTarget();
                }

            } catch (IOException e) {
                telaPrincipalHandler.obtainMessage(MENSAGEM_DESCONEXAO, e.getMessage() + "[THREAD_COMUNICACAO]").sendToTarget();
                e.printStackTrace();
            }
        }
    }
}
