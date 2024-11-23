package com.example.yttest;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yttest.ml.Adobov1;
import com.example.yttest.ml.Adobov2;
import com.example.yttest.ml.Combineadobov;
import com.example.yttest.ml.Goated;
import com.example.yttest.ml.Incfinal;
import com.example.yttest.ml.Lastfinal;
import com.example.yttest.ml.Nix;
import com.example.yttest.ml.Scadobov1;
import com.example.yttest.ml.Tentest;
import com.example.yttest.ml.Tentestmoreepoch;
import com.example.yttest.ml.Testtwo;
import com.example.yttest.ml.Ultimateadobov;
import com.google.common.util.concurrent.ListenableFuture;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

    private boolean isFlashOn = false;

    private PreviewView previewView;
    ImageView capture, flash, backCameraButton, gallery;
    ActivityResultLauncher<Intent> galleryLauncher;
    int cameraFacing = CameraSelector.LENS_FACING_BACK;
    private final ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean o) {
            if(o){
                startCamera(cameraFacing);

            }
        }
    });





    Bitmap image;
    ImageView imageView, camera;
    TextView result;

//    String stats;
//    TextView statsTextView;
    public static Bitmap poatatoimage;
    int imageSize = 32;

    String modelName = "mymodel.tflite";


    public void classifyImage(Bitmap image)
    {
        try {
            Incfinal model = Incfinal.newInstance(getApplicationContext());

            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 32, 32, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int[] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
            int pixel = 0;
            //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
            for(int i = 0; i < imageSize; i ++){
                for(int j = 0; j < imageSize; j++){
                    int val = intValues[pixel++]; // RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 1));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 1));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 1));
                }
            }



            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            Incfinal.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();


            ArrayList percentages = new ArrayList<>();

            float[] confidences = outputFeature0.getFloatArray();
            // find the index of the class with the biggest confidence.
            int maxPos = 0;
            float maxConfidence = 0;
            int totalsz = 0;
            for (int i = 0; i < confidences.length; i++) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i];
                    maxPos = i;


                }
                totalsz += confidences[i];
            }
            // REVISION
            Log.d("Confidences", Arrays.toString(confidences));
            Log.d("pierre", "pierre");

            for (int i = 0; i < confidences.length; i++) {

                   //percentages


            }



            //String[] classes = {"Adobo", "Dinuguan", "Laing"};
            String[] classes = {"Adobo", "Arroz Caldo", "Bicol Express", "Champorado",
                    "Dinuguan", "Kare Kare", "Laing", "Pinakbet", "Sinigang", "Tinola"};
           // Toast.makeText(MainActivity.this, Arrays.toString(confidences) + "" + classes[maxPos], Toast.LENGTH_LONG).show();

            TextView stats2 = findViewById(R.id.stats2);

            float[] probabilities = softmax(confidences);

            String foodDetected = classes[maxPos];

            float maxProbability = 0;
            for (int i = 0; i < probabilities.length; i++) {
                if (probabilities[i] > maxProbability) {
                    maxProbability = probabilities[i];

                }

            }

//            if (maxProbability < 0.70){
//                foodDetected = "Uncertain";
//            }



            stats2.setText(Arrays.toString(probabilities) + "" + foodDetected);
            //result.setText(classes[maxPos]);

//            stats = "adobo " + confidences[0] + "dinuguan " + confidences[1] + "laing " + confidences[2];
//            statsTextView = findViewById(R.id.stats);
//            statsTextView.setText(stats);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();





            Intent intent = new Intent(MainActivity.this, Fooddetected.class);

            intent.putExtra("textResult",  foodDetected);
            intent.putExtra("imageResult", byteArray);



            startActivity(intent);





            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
    }






/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(resultCode == RESULT_OK){
            if(requestCode == 3){

                image = (Bitmap) data.getExtras().get("data");
                int dimension = Math.min(image.getWidth(), image.getHeight());
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                imageView.setImageBitmap(image);




                ImageHolder singleton = ImageHolder.getInstance();
                singleton.setBitmap(image);

                image = Bitmap.createScaledBitmap(image,imageSize, imageSize, false);
                classifyImage(image);




            }else{
                Uri dat = data.getData();
                Bitmap image = null;
                try {
                    image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dat);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageView.setImageBitmap(image);

                image = Bitmap.createScaledBitmap(image,imageSize, imageSize, false);
                classifyImage(image);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

 */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        previewView = findViewById(R.id.previewView);
        capture = findViewById(R.id.b);
        if(ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            activityResultLauncher.launch(Manifest.permission.CAMERA);
        }else{
            startCamera(cameraFacing);
        }

//        backCameraButton = findViewById(R.id.btnBackCamera);
//        backCameraButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });

        gallery = findViewById(R.id.galleryButton);

        // Initialize the gallery launcher
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        try {
                            Bitmap image = BitmapFactory.decodeStream(
                                    getContentResolver().openInputStream(imageUri)
                            );

                            ImageHolder singleton = ImageHolder.getInstance();
                            singleton.setBitmap(image);

//                            // Display the selected image in an ImageView if needed
//                            imageView = findViewById(R.id.imageView); // Ensure this ImageView exists in your layout
//                            imageView.setImageBitmap(image);

                            // Resize the image and pass it to classifyImage
                            Bitmap scaledImage = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                            classifyImage(scaledImage);

                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        // Set the galleryButton click listener
        gallery.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryLauncher.launch(intent);
        });

        // Other initialization code...




        flash = findViewById(R.id.imageView7);

        //gallery = findViewById(R.id.button2);


        /*
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 3);
                }else{
                    requestPermissions(new String[]{android.Manifest.permission.CAMERA}, 100);
                }
            }
        });

         */



        /*
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent cameraIntent = new Intent(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI));
                    startActivityForResult(cameraIntent, 3);

            }
        });

         */



    }

    public void startCamera(int cameraFacing){
        int aspectRatio = aspectRatio(previewView.getWidth(), previewView.getHeight());
        ListenableFuture listenableFuture = ProcessCameraProvider.getInstance(this);

        listenableFuture.addListener(()->{
            try{
                ProcessCameraProvider cameraProvider = (ProcessCameraProvider) listenableFuture.get();
                Preview preview = new Preview.Builder().setTargetAspectRatio(aspectRatio).build();
                ImageCapture imageCapture = new ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY).setTargetRotation(getWindowManager().getDefaultDisplay().getRotation()).build();
                CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(cameraFacing).build();
                cameraProvider.unbindAll();
                Camera camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);


                // Old camera preview

                capture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Bitmap image = previewView.getBitmap();

                        ImageHolder singleton = ImageHolder.getInstance();
                        singleton.setBitmap(image);

                        image = Bitmap.createScaledBitmap(image,imageSize, imageSize, false);
                        classifyImage(image);



                    }
                });

                // New camera preview
//                capture.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Bitmap image = previewView.getBitmap();
//
//                        // Convert dp to pixels if needed
//                        int dpToPx = (int) (280 * getResources().getDisplayMetrics().density);
//
//                        // Use the fixed size of the square overlay
//                        int squareSize = dpToPx; // 280px
//
//                        // Calculate offsets for cropping
//                        int xOffset = (image.getWidth() - squareSize) / 2;
//                        int yOffset = (image.getHeight() - squareSize) / 2;
//
//                        // Crop the image to the square overlay dimensions
//                        Bitmap croppedImage = Bitmap.createBitmap(image, xOffset, yOffset, squareSize, squareSize);
//
//                        ImageHolder singleton = ImageHolder.getInstance();
//                        singleton.setBitmap(croppedImage);
//
//                        // Scale and classify the image
//                        Bitmap scaledImage = Bitmap.createScaledBitmap(croppedImage, imageSize, imageSize, false);
//                        classifyImage(scaledImage);
//                    }
//                });


                flash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setFlashIcon(camera);

                        isFlashOn = !isFlashOn;
                        if (isFlashOn) {
                            flash.setImageResource(R.drawable.flash_on_icon);
                            // Turn on the flash
                        } else {
                            flash.setImageResource(R.drawable.flash_off_icon);
                            // Turn off the flash
                        }
                    }
                });


                preview.setSurfaceProvider(previewView.getSurfaceProvider());

            }catch (ExecutionException | InterruptedException e){
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));



    }

    private void setFlashIcon(Camera camera){
        if(camera.getCameraInfo().hasFlashUnit()){
            if(camera.getCameraInfo().getTorchState().getValue() == 0){
                camera.getCameraControl().enableTorch(true);

            }else{
                camera.getCameraControl().enableTorch(false);
            }
        }

    }

    /////////SOFTMAX
    public static float[] softmax(float[] logits) {
        float maxLogit = Float.NEGATIVE_INFINITY;
        for (float logit : logits) {
            if (logit > maxLogit) {
                maxLogit = logit;
            }
        }

        float sum = 0.0f;
        for (int i = 0; i < logits.length; i++) {
            logits[i] = (float) Math.exp(logits[i] - maxLogit);
            sum += logits[i];
        }

        for (int i = 0; i < logits.length; i++) {
            logits[i] /= sum;
        }

        return logits;
    }




    private int aspectRatio(int width, int height){
        double previewRatio = (double) Math.max(width,height) / Math.min(width,height);

        if(Math.abs(previewRatio - 4.0/3.0) <= Math.abs(previewRatio - 16.0/9.0)){
            return AspectRatio.RATIO_4_3;
        }
        return AspectRatio.RATIO_16_9;
    }
}