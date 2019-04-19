package www.cheemarket.com.javadesmaeili;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import www.cheemarket.com.javadesmaeili.Adapter.Adapter;
import www.cheemarket.com.javadesmaeili.Adapter.SubdastebandiAdapter;
import www.cheemarket.com.javadesmaeili.Structure.KalaStructure;


public class Subdastebandi extends AppCompatActivity {

    private String code = "";
    public static ArrayList<www.cheemarket.com.javadesmaeili.Structure.Subdastebandi> mdatasetListsubdastebandi;
    public static ArrayList<KalaStructure> mdatasetkalafortwokala;
    public static ArrayList<KalaStructure> mdatasetkalaforonekala;
    public static LinearLayoutManager layoutManager = null;


    public static RecyclerView.Adapter AdapterListsubcategory;
    public static Adapter AdapterListkala;

    public static RecyclerView List = null;
    public static long Listnumber = 0;
    public static boolean showsubdastebandi = false;
    static ImageButton imgbtnview;
    static ImageButton imgbtnsort;
    private static TextView pagetitle;
    private static boolean allownext = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subdastebandi);
        G.CurrentActivity = this;


        List = (RecyclerView) findViewById(R.id.List);
        imgbtnview = (ImageButton) findViewById(R.id.imgbtnview);
        imgbtnsort = (ImageButton) findViewById(R.id.imgbtnsort);
        pagetitle = (TextView) findViewById(R.id.txttitle);
        clearalldata();
        Bundle extras = getIntent().getExtras();
        /*
        if (extras != null) {

            if (extras.containsKey("subdastebandistring")) //tanagholat
             {
                 code = extras.getString("subdastebandistring");
                 namayeshsubdastebandi(code);
            }
            else  if (extras.containsKey("subkala"))// kalahaye 400
            {
                code = extras.getString("subkala");

            }
        }
*/
        namayeshkalaha("4049", "are");
        imgbtnview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AdapterListkala !=null){

                    AdapterListkala.changelayout(List,AdapterListkala,layoutManager);
                }

            }
        });
    }

    private static void clearalldata() {
        Log.i("LOG", "clearalldata");
        if (mdatasetkalafortwokala != null) {
            mdatasetkalafortwokala.clear();
        }
        if (mdatasetListsubdastebandi != null) {
            mdatasetListsubdastebandi.clear();
        }
        if (AdapterListsubcategory != null) {
            AdapterListsubcategory.notifyDataSetChanged();
        }


        mdatasetListsubdastebandi = null;
        mdatasetkalafortwokala = null;
        layoutManager = null;
        AdapterListsubcategory = null;
        AdapterListkala = null;

        Listnumber = 0;
        showsubdastebandi = false;

        pagetitle.setVisibility(View.GONE);
        pagetitle.setText("");
        imgbtnsort.setVisibility(View.GONE);
        imgbtnview.setVisibility(View.GONE);
    }

    public static void namayeshsubdastebandi(String code) throws NullPointerException {

        clearalldata();
        showsubdastebandi = true;
        mdatasetListsubdastebandi = new ArrayList<>();
        List.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(G.CurrentActivity);
        List.setLayoutManager(layoutManager);
        AdapterListsubcategory = new SubdastebandiAdapter(mdatasetListsubdastebandi);
        List.setAdapter(AdapterListsubcategory);


        ArrayList<Webservice.requestparameter> array1 = new ArrayList<>();
        Webservice.requestparameter object1 = new Webservice.requestparameter();
        object1.key = "title";
        object1.value = code;
        Log.i("LOG", "CODE =" + code);
        array1.add(object1);
        Webservice.request("Store.php?action=getsubdastebandi", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();
                if (input.equals("[]")) {
                    return;
                }
                try {
                    JSONArray array = new JSONArray(input);
                    for (int i = 0; i < array.length(); i++) {
                        final JSONObject object = array.getJSONObject(i);
                        www.cheemarket.com.javadesmaeili.Structure.Subdastebandi subdastebandi = new www.cheemarket.com.javadesmaeili.Structure.Subdastebandi();
                        subdastebandi.Subdastebandi = object.getString("Subdastebandi");
                        subdastebandi.Title = object.getString("Title");
                        subdastebandi.Image = object.getString("Image");
                        subdastebandi.Id = object.getString("Id");
                        mdatasetListsubdastebandi.add(subdastebandi);
                    }

                    G.HANDLER.post(new Runnable() {
                        @Override
                        public void run() {
                            AdapterListsubcategory.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {

                }


            }
        }, array1);


    }


    public static void namayeshkalaha(final String code, String title) throws NullPointerException {
        clearalldata();
        showsubdastebandi = false;
        Log.i("LOG", "code =" + code + " ?? title =" + title);
        pagetitle.setVisibility(View.VISIBLE);
        pagetitle.setText(title);
        imgbtnsort.setVisibility(View.VISIBLE);
        imgbtnview.setVisibility(View.VISIBLE);


        layoutManager = new LinearLayoutManager(G.CurrentActivity);
        mdatasetkalafortwokala = new ArrayList<KalaStructure>();
        mdatasetkalaforonekala =  new ArrayList<KalaStructure>();
        AdapterListkala = new Adapter(mdatasetkalaforonekala, mdatasetkalafortwokala, R.layout.listtwo);

        List.setHasFixedSize(true);
        List.setLayoutManager(layoutManager);
        List.setAdapter(AdapterListkala);
        List.setNestedScrollingEnabled(false);


        final Callback mycall = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();
                allownext = false;

                if(input.equals("[]")){
                    return;
                }
                try {
                    JSONArray   array = new JSONArray(input);
                    KalaStructure kala  = new KalaStructure();
                    boolean kalaone = true;
                    Listnumber += array.length();

                    for(int i = 0 ; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        if(i == 0) {
                            if (mdatasetkalafortwokala.size() > 0) {

                                if (mdatasetkalafortwokala.get(mdatasetkalafortwokala.size() - 1).Id2 == null || mdatasetkalafortwokala.get(mdatasetkalafortwokala.size() - 1).Id2.equals("")) {
                                    Converts.convertinputdata(object, mdatasetkalafortwokala.get(mdatasetkalafortwokala.size() - 1),false);
                                }

                            }
                        }


                        KalaStructure temp = new KalaStructure();
                        Converts.convertinputdata(object,temp,true);
                        mdatasetkalaforonekala.add(temp);


                        if(kalaone){
                            kala  = new KalaStructure();
                            Converts.convertinputdata(object,kala,kalaone);
                        }else {
                            Converts.convertinputdata(object,kala,kalaone);
                            mdatasetkalafortwokala.add(kala);
                        }
                        kalaone = !kalaone;
                    }


                    G.HANDLER.post(new Runnable() {
                        @Override
                        public void run() {

                            AdapterListkala.notifyItemInserted(AdapterListkala.getItemCount() + 1);
                        }
                    });
                    allownext  = true;

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        };


        ArrayList<Webservice.requestparameter> array1 = new ArrayList<>();
        Webservice.requestparameter object1 = new Webservice.requestparameter();
        object1.key = "value";
        object1.value = code;
        array1.add(object1);
        Webservice.requestparameter object2 = new Webservice.requestparameter();
        object2.key = "number";
        object2.value = Listnumber + "";
        array1.add(object2);
        Webservice.request("Store.php?action=partofdata", mycall, array1);


        List.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

                if (showsubdastebandi) {
                    return;
                }
                TextView txtname = (TextView) view.findViewById(R.id.txtnameone);
                if (txtname.getText().toString().equals(mdatasetkalafortwokala.get(mdatasetkalafortwokala.size() - 2).Name1) || txtname.getText().toString().equals(mdatasetkalafortwokala.get(mdatasetkalafortwokala.size() - 1).Name1)) {
                    if (layoutManager.findLastVisibleItemPosition() == (mdatasetkalafortwokala.size() - 1) - 1) {

                        if (allownext == false) {
                            return;
                        }
                        allownext = false;



                        ArrayList<Webservice.requestparameter> array1 = new ArrayList<>();
                        Webservice.requestparameter object1 = new Webservice.requestparameter();
                        object1.key = "value";
                        object1.value = code;
                        array1.add(object1);
                        Webservice.requestparameter object2 = new Webservice.requestparameter();
                        object2.key = "number";
                        object2.value = Listnumber + "";
                        array1.add(object2);
                        Webservice.request("Store.php?action=partofdata" ,mycall,array1);

                    }
                }

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {


            }
        });




    }


}
