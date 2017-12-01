package br.edu.ifspsaocarlos.sdm.sitesinteressantes.helper;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.edu.ifspsaocarlos.sdm.sitesinteressantes.model.Site;

/**
 * Created by Pio Tofanelli on 01-Dec-17.
 */

public class SiteHelper {
    private static final String SHARED_PREFERENCES_SITES = "sites";
    private SharedPreferences preferences;

    private List<Site> sites;

    public SiteHelper(SharedPreferences preferences) {
        this.preferences = preferences;
        carregarSites();
    }

    public List<Site> getSites() {
        return sites;
    }

    public void inserirSite(Site site) {
        sites.add(site);

        StringBuilder builder = new StringBuilder();
        SharedPreferences.Editor editor = preferences.edit();

        for(Site site1 : sites) {
            builder.append(site1.toString()+";");
        }

        editor.putString(SHARED_PREFERENCES_SITES, builder.toString());
        editor.commit();
    }

    private void carregarSites() {
        sites = new ArrayList<Site>();
        String[] siteStrings = preferences.getString(SHARED_PREFERENCES_SITES, "").split(";");

        for (int i = 0 ; i < siteStrings.length ; i++) {
            String[] siteString = siteStrings[i].split("-");
            if(siteString.length == 2) {
                Site site = new Site(siteString[0], Integer.parseInt(siteString[1]));
                sites.add(site);
            }
        }
    }
}
