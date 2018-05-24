package br.edu.ifspsaocarlos.sdm.pa2trabalho.model;

import java.util.List;

/**
 * Created by ptofanelli on 18-Apr-18.
 */

public class ContatoProtocol {

    public String appId;

    public boolean group;

    public List<Short> groupIds;

    public ContatoProtocol() {}

    public ContatoProtocol(String protocol) {
        StringBuilder builder = new StringBuilder(protocol);

        extractAppId(builder);
    }

    private void extractAppId(StringBuilder protocol) {
        appId = protocol.substring(0, 2);
    }

    private void extractType(StringBuilder protocol) {
        group = protocol.charAt(3) == 'G';
    }

    private void extractGroupIds() {
        
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public boolean isGroup() {
        return group;
    }

    public void setGroup(boolean group) {
        this.group = group;
    }

    public List<Short> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<Short> groupIds) {
        this.groupIds = groupIds;
    }
}
