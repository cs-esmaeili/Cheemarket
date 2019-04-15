package www.cheemarket.com.javadesmaeili;

import android.app.Activity;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import www.cheemarket.com.javadesmaeili.Structure.KalaStructure;

public class Converts {
    //

    public static KalaStructure convertinputdata(JSONObject jsonObject) {

        KalaStructure kalaStructure = new KalaStructure();
        try {
            kalaStructure.Name1 = jsonObject.getString("Name");
            kalaStructure.Berand1 = jsonObject.getString("Berand");
            kalaStructure.Dastebandi1 = jsonObject.getString("Dastebandi");
            kalaStructure.Code1 = jsonObject.getString("Code");
            kalaStructure.Weight1 = jsonObject.getString("Weight");
            kalaStructure.Price1 = jsonObject.getString("Price");
            kalaStructure.oldPrice1 = jsonObject.getString("oldPrice");
            kalaStructure.Volume1 = jsonObject.getString("Volume");
            kalaStructure.Image1 = jsonObject.getString("Image");
            kalaStructure.Tozihat1 = jsonObject.getString("Tozihat");
            kalaStructure.Ordernumber1 = jsonObject.getString("Ordernumber");
            kalaStructure.Datetime1 = jsonObject.getString("Datetime");
            kalaStructure.Available1 = jsonObject.getString("Available");
            kalaStructure.SpecialPrice1 = jsonObject.getString("SpecialPrice");
            kalaStructure.Id1 = jsonObject.getString("Id");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return kalaStructure;
    }

    public static void openactivity(ArrayList<KalaStructure> mdataset, int position, Class<? extends Activity> target) {


        Intent intent = new Intent(G.CurrentActivity, target);

        intent.putExtra("Name", mdataset.get(position).Name1);
        intent.putExtra("Berand", mdataset.get(position).Berand1);
        intent.putExtra("Dastebandi", mdataset.get(position).Dastebandi1);
        intent.putExtra("Code", mdataset.get(position).Code1);
        intent.putExtra("Weight", mdataset.get(position).Weight1);
        intent.putExtra("Price", mdataset.get(position).Price1);
        intent.putExtra("oldPrice", mdataset.get(position).oldPrice1);
        intent.putExtra("Volume", mdataset.get(position).Volume1);
        intent.putExtra("Image", mdataset.get(position).Image1);
        intent.putExtra("Tozihat", mdataset.get(position).Tozihat1);
        intent.putExtra("Ordernumber", mdataset.get(position).Ordernumber1);
        intent.putExtra("Available", mdataset.get(position).Available1);
        intent.putExtra("Datetime", mdataset.get(position).Datetime1);
        intent.putExtra("SpecialPrice", mdataset.get(position).SpecialPrice1);
        intent.putExtra("Id", mdataset.get(position).Id1);
        G.CurrentActivity.startActivity(intent);


    }


    public static void openactivity(JSONObject jsonObject, Class<? extends Activity> target) {

        try {
            Intent intent = new Intent(G.CurrentActivity, target);
            intent.putExtra("Name", jsonObject.getString("Name"));
            intent.putExtra("Berand", jsonObject.getString("Berand"));
            intent.putExtra("Dastebandi", jsonObject.getString("Dastebandi"));
            intent.putExtra("Code", jsonObject.getString("Code"));
            intent.putExtra("Weight", jsonObject.getString("Weight"));
            intent.putExtra("Price", jsonObject.getString("Price"));
            intent.putExtra("oldPrice", jsonObject.getString("oldPrice"));
            intent.putExtra("Volume", jsonObject.getString("Volume"));
            intent.putExtra("Image", jsonObject.getString("Image"));
            intent.putExtra("Tozihat", jsonObject.getString("Tozihat"));
            intent.putExtra("Ordernumber", jsonObject.getString("Ordernumber"));
            intent.putExtra("Available", jsonObject.getString("Available"));
            intent.putExtra("Datetime", jsonObject.getString("Datetime"));
            intent.putExtra("SpecialPrice", jsonObject.getString("SpecialPrice"));
            intent.putExtra("Id", jsonObject.getString("Id"));
            G.CurrentActivity.startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}

