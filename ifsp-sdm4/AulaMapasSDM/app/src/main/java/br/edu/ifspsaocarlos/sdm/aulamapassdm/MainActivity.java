package br.edu.ifspsaocarlos.sdm.aulamapassdm;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends AppCompatActivity
        implements  OnMapReadyCallback,
                    GoogleMap.OnMyLocationButtonClickListener,
                    GoogleMap.OnCameraIdleListener,
                    GoogleMap.OnMapClickListener,
                    LocationListener{
    //referencia pro mapa
    private GoogleMap mapa;

    public static final int CODIGO_REQUISICAO_PERMISSAO_LOCALIZACAO = 1;
    public static final int CODIGO_REQUISICAO_ATIVACAO_LOCALIZACAO = 2;

    private static final double LATITUDE_INICIAL = -21.970306;
    private static final double LONGITUDE_INICIAL = -47.878733;

    private static final int DELAY_ANIMACAO = 3000;

    private static final float INCLINACAO_CAMERA = 50;

    private static final float ROTACAO_CAMERA =90;
    private static final float ZOOM = 17.5f;

    private final long INTERVALO_ATUALIZACAO_LOCALIZACAO = 10000;
    private final float DISTANCIA_MINIMA_LOCALIZACAO = 20;

    private LocationManager locationManager;

    private LatLng ultimaCoordenada;
    private PolygonOptions polygonOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_mapa);

        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        int fineLocPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int coarseLocPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if(fineLocPermission != PackageManager.PERMISSION_GRANTED && coarseLocPermission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, CODIGO_REQUISICAO_PERMISSAO_LOCALIZACAO);
            }
        } else {
            if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                Intent ativaLocalizacaoIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(ativaLocalizacaoIntent, CODIGO_REQUISICAO_ATIVACAO_LOCALIZACAO);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, INTERVALO_ATUALIZACAO_LOCALIZACAO, DISTANCIA_MINIMA_LOCALIZACAO, this);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, INTERVALO_ATUALIZACAO_LOCALIZACAO, DISTANCIA_MINIMA_LOCALIZACAO, this);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        locationManager.removeUpdates(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mapa = googleMap;

        mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mapa.getUiSettings().setZoomControlsEnabled(true);
        mapa.setIndoorEnabled(true);
        mapa.setBuildingsEnabled(true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            //pede as permissões
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},CODIGO_REQUISICAO_PERMISSAO_LOCALIZACAO);
            }

        }else{
            mapa.setMyLocationEnabled(true);
        }

        /*LatLng novaLatLng = new LatLng(-23.545009,-46.6538594);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(novaLatLng);

        mapa.moveCamera(cameraUpdate);*/

        LatLng latLngInicial = new LatLng(LATITUDE_INICIAL,LONGITUDE_INICIAL);

        CameraPosition.Builder camBuilder = new CameraPosition.Builder();

        camBuilder.bearing(ROTACAO_CAMERA);
        camBuilder.zoom(ZOOM);
        camBuilder.tilt(INCLINACAO_CAMERA);
        camBuilder.target(latLngInicial);

        CameraPosition cameraPosition = camBuilder.build();

        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);

        mapa.animateCamera(cameraUpdate, DELAY_ANIMACAO, new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this,"Centralizado",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this,"Cancelado",Toast.LENGTH_LONG).show();
            }
        });

        MarkerOptions markerOptions =new MarkerOptions();

        markerOptions.position(latLngInicial);
        markerOptions.title("IFSP");

        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.carrot_marker));

        mapa.addMarker(markerOptions);

        mapa.setOnMyLocationButtonClickListener(this);
        mapa.setOnCameraIdleListener(this);
        mapa.setOnMapClickListener(this);

        ultimaCoordenada = latLngInicial;
        polygonOptions = new PolygonOptions();
        polygonOptions.strokeColor(Color.BLUE);
        polygonOptions.add(latLngInicial);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CODIGO_REQUISICAO_PERMISSAO_LOCALIZACAO) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                mapa.setMyLocationEnabled(true);

            }else{
                Toast.makeText(this,"Permissões Necessáris",Toast.LENGTH_LONG);
            }

        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "Centralizando na posicao do usuario", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onCameraIdle() {
        LatLng novaCoordenada = mapa.getCameraPosition().target;
        Toast.makeText(this, "nova coord: " + novaCoordenada.latitude + ", " + novaCoordenada.longitude, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if(latLng.latitude != ultimaCoordenada.latitude || latLng.longitude != ultimaCoordenada.longitude) {
            mapa.clear();

            MarkerOptions markerOptionsUltimaCoord = new MarkerOptions();
            markerOptionsUltimaCoord.title("Ultima coordenada");
            markerOptionsUltimaCoord.position(ultimaCoordenada);
            mapa.addMarker(markerOptionsUltimaCoord);

            MarkerOptions markerOptionsNovaCoord = new MarkerOptions();
            markerOptionsNovaCoord.title("Nova coordenada");
            markerOptionsNovaCoord.position(latLng);
            mapa.addMarker(markerOptionsNovaCoord);

            mapa.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Toast.makeText(MainActivity.this, marker.getTitle(), Toast.LENGTH_SHORT).show();
                    return false;
                }
            });

            //Polyline
            /*
            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.add(ultimaCoordenada);
            polylineOptions.add(latLng);
            polylineOptions.color(Color.RED);
            mapa.addPolyline(polylineOptions);
            */

            //Polygon


            mapa.animateCamera(CameraUpdateFactory.newLatLng(latLng));

            ultimaCoordenada = latLng;

            polygonOptions.add(ultimaCoordenada);
            mapa.addPolygon(polygonOptions);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CODIGO_REQUISICAO_ATIVACAO_LOCALIZACAO) {
            if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                Toast.makeText(this, "Localizacao e necessaria", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mapa.clear();
        LatLng novaCoord = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(novaCoord);
        mapa.moveCamera(cameraUpdate);
        mapa.addMarker(new MarkerOptions().position(novaCoord));nbhhmnj
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
