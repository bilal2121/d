package example.sony.com.mobile_wallet;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by SONY on 1/7/2017.
 */

 class LongOperation  extends AsyncTask<String, Void, Void> {

    // Required initialization


    private String Content;
    private String Error = null;
   // private ProgressDialog Dialog = new ProgressDialog(RestFulWebservice.this);
    String data ="";
    //TextView uiUpdate = (TextView) findViewById(R.id.output);
    //TextView jsonParsed = (TextView) findViewById(R.id.jsonParsed);
    int sizeData = 0;
   // EditText serverText = (EditText) findViewById(R.id.serverText);


    protected void onPreExecute() {
        // NOTE: You can call UI Element here.

        //Start Progress Dialog (Message)

       // Dialog.setMessage("Please wait..");
       // Dialog.show();
        JSONObject ob= new JSONObject();
        try {
            ob.put("data","dhf");
            ob.put("name","bilal");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // try{
        // Set Request parameter
        data =ob.toString();

        //} catch (UnsupportedEncodingException e) {
        // TODO Auto-generated catch block
        //	e.printStackTrace();
        //}

    }

    // Call after onPreExecute method
    protected Void doInBackground(String... urls) {

        /************ Make Post Call To Web Server ***********/
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL(urls[0]);

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }

            // Append Server Response To Content String
            Content = sb.toString();

        }
        catch(Exception ex)
        {
            Error = ex.getMessage();
        }
        finally
        {
            try
            {

                reader.close();

            }

            catch(Exception ex) {}
        }

        /*****************************************************/
        return null;
    }

    protected void onPostExecute(Void unused) {
        // NOTE: You can call UI Element here.

        // Close progress dialog
       // Dialog.dismiss();

        if (Error != null) {

        //    uiUpdate.setText("Output : "+Error);

        } else {

            // Show Response Json On Screen (activity)
           // uiUpdate.setText( Content );

            /****************** Start Parse Response JSON Data *************/

            String OutputData = "";
            JSONObject jsonResponse;

            try {

                /****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
                jsonResponse = new JSONObject(Content);

                /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
                /*******  Returns null otherwise.  *******/
                JSONArray jsonMainNode = jsonResponse.optJSONArray("Android");

                /*********** Process each JSON Node ************/

                int lengthJsonArr = jsonMainNode.length();

                for(int i=0; i < lengthJsonArr; i++)
                {
                    /****** Get Object for each JSON node.***********/
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

                    /******* Fetch node values **********/
                    String name       = jsonChildNode.optString("name").toString();
                    String number     = jsonChildNode.optString("number").toString();
                    String date_added = jsonChildNode.optString("date_added").toString();


                    OutputData += " Name 		    : "+ name +" \n "
                            + "Number 		: "+ number +" \n "
                            + "Time 				: "+ date_added +" \n "
                            +"--------------------------------------------------\n";

                    //Log.i("JSON parse", song_name);
                }
                /****************** End Parse Response JSON Data *************/

                //Show Parsed Output on screen (activity)
                //jsonParsed.setText( OutputData );


            } catch (JSONException e) {

                e.printStackTrace();
            }


        }
    }

}
