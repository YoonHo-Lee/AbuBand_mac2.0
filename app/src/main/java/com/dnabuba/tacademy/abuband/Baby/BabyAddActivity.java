package com.dnabuba.tacademy.abuband.Baby;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.dnabuba.tacademy.abuband.MainActivity;
import com.dnabuba.tacademy.abuband.Member.LoginFragment;
import com.dnabuba.tacademy.abuband.NetworkManager;
import com.dnabuba.tacademy.abuband.PropertyManager;
import com.dnabuba.tacademy.abuband.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BabyAddActivity extends AppCompatActivity {

    private static final String TEMP_PHOTO_FILE = "temporary_holder.jpg";
    private static final String TEMP_CAMERA_FILE = "temporary_camera.jpg";

    private static final int REQUEST_GALLERY = 0;
    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_CROP = 2;

    private static final int TAG_RESULT_BABY_ADD = 1;

    ImageView baby_image;

    EditText babyName, babyBirth, babyGender;
    String babyBirth_num;
    String babyGender_num;
    BabyAdapter babyAdapter;

    File baby_image_file;

    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_add);
        setTitle(getString(R.string.title_baby_add));

        Intent intent = getIntent();
        flag = intent.getIntExtra(BabyListFragment.TAG_BABYFLAG, 999);

        if(flag == 777) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        babyName = (EditText) findViewById(R.id.edit_babyName);
        babyBirth = (EditText) findViewById(R.id.edit_babyBirth);
        babyGender = (EditText) findViewById(R.id.edit_babyGender);

        baby_image = (ImageView) findViewById(R.id.btn_babyImage);
        baby_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageDialog();
            }
        });



        /***************** 생년월일 설정 *******************/
        babyBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                birthDialog();
            }
        });

        /***************** 성별 설정 *******************/
        babyGender.setOnClickListener(new View.OnClickListener() {
            int selectPosition;

            @Override
            public void onClick(View v) {
                selectPosition = 0;
                final String[] gender = {"남아", "여아"};
                AlertDialog.Builder builder = new AlertDialog.Builder(BabyAddActivity.this);
                builder.setTitle("성별");
                builder.setSingleChoiceItems(gender, selectPosition, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectPosition = which;
                    }
                });
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        babyGender.setText(gender[selectPosition]);
                        babyGender_num = selectPosition + "";
                    }
                });
                builder.create().show();
            }
        });


        /***************** 아이 추가 *******************/
        Button btn = (Button) findViewById(R.id.btn_babyAdd);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(babyName.getText().toString()) && !TextUtils.isEmpty(babyBirth.getText().toString()) && !TextUtils.isEmpty(babyGender.getText().toString())) {
                    //editText의 내용을 네트워크 쪽으로 보내기
                    try {
                        addBaby(baby_image_file, babyName.getText().toString(), babyBirth_num, babyGender_num);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(BabyAddActivity.this, "빈칸을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*****************
     * 아이추가 네트워크 불러오기
     *******************/
    private void addBaby(final File image, final String name, final String birth, final String gender) throws FileNotFoundException {
        NetworkManager.getInstance().setBabyAdd(BabyAddActivity.this,image, name, birth, gender, new NetworkManager.OnResultListener<BabyaddCodeResult>() {
            @Override
            public void onSuccess(BabyaddCodeResult result) {
                switch (result.code)    {
                    case 1:
                        if(baby_image_file != null) {
                            setBabyImage();
                        }
                        PropertyManager.getInstance().setPrefBaby(result.id); // 선택된 아이 아이디 저장
//                        intent.putExtra(LoginFragment.TAG_BABY_IMAGE,result.image);
//                        intent.putExtra(LoginFragment.TAG_BABY_NAME, result.name);
//                        intent.putExtra(LoginFragment.TAG_BABY_BIRTH, result.birth);
                        PropertyManager.getInstance().setPrefBaby_Image(result.image);
                        PropertyManager.getInstance().setPrefBaby_Name(result.name);
                        PropertyManager.getInstance().setPrefBaby_Birth(result.birth);
                        Log.e("BabyAddActivity",result.image + result.name + result.birth);
                        setResult(TAG_RESULT_BABY_ADD);

                        if(flag == 777)  {
                            finish();
                        } else if(flag == 999)  {
                            Intent intent = new Intent(BabyAddActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        break;
                }
                Toast.makeText(BabyAddActivity.this, name + birth + gender + "등록", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(int code) {
//                Toast.makeText(BabyAddActivity.this, "error : " + code, Toast.LENGTH_SHORT).show();
                Log.e("BabyAddActivity", "addBaby Fail" + code);
            }
        });
    }

    private void setBabyImage() {
//        NetworkManager.getInstance().setBabyImage();
    }

    /*****************
     * 생년월일 다이얼로그
     *******************/
    /*커스텀 다이얼로그 불러오기*/
    /*년도 최대값을 올해로 설정하게, 캘린더로 현재 연도 가져오기.*/
    public void birthDialog() {
        final Dialog d = new Dialog(BabyAddActivity.this);
        d.setTitle("생년월일");
        d.setContentView(R.layout.dialog_baby_add_birth);
//                아마도 가로세로 크기 지정???????????????????????????????????
//        d.getWindow().setLayout(디멘으로 값지정);

        final NumberPicker npY, npM, npD;

        npY = (NumberPicker) d.findViewById(R.id.numberPickerY);
        npY.setMaxValue(2015);
        npY.setMinValue(1900);
        npY.setValue(2000);
        npY.setWrapSelectorWheel(false);

        npM = (NumberPicker) d.findViewById(R.id.numberPickerM);
        npM.setMaxValue(12);
        npM.setMinValue(1);
        npM.setWrapSelectorWheel(false);

        npD = (NumberPicker) d.findViewById(R.id.numberPickerD);
        npD.setMaxValue(31);
        npD.setMinValue(1);
        npD.setWrapSelectorWheel(false);

        Button btn_ok;
        btn_ok = (Button) d.findViewById(R.id.btn_birth_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //한자리수 월 일 앞에 0 붙이기
                StringBuilder sb = new StringBuilder();
                String Y, M, D;
                Y = npY.getValue() + "";
                M = npM.getValue() + "";
                D = npD.getValue() + "";
                if (String.valueOf(npM.getValue()).length() == 1) {
                    M = "0" + npM.getValue();
                }
                if (String.valueOf(npD.getValue()).length() == 1) {
                    D = "0" + npD.getValue();
                }
                sb.append(Y);
                sb.append(M);
                sb.append(D);
                babyBirth_num = sb.toString(); //서버로 보내는 값
                babyBirth.setText(String.valueOf(npY.getValue()) + " / " + String.valueOf(npM.getValue()) + " / " + String.valueOf(npD.getValue()));
                d.dismiss();
            }
        });

        d.show();
    }

    private void onImageDialog() {
        //out of memory로 인해 카메라 잠시 주석....ㅠㅠ
//        String[] items = {"갤러리","카메라"};
        String[] items = {"갤러리"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_menu_camera);
        builder.setTitle("사진등록");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which)  {
                    case 0: // 캘러리
                        Intent photoPickerIntent = new Intent(
                                Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        photoPickerIntent.setType("image/*");
                        photoPickerIntent.putExtra("crop", "true");
                        photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
                        photoPickerIntent.putExtra("outputFormat",
                                Bitmap.CompressFormat.JPEG.toString());
                        startActivityForResult(photoPickerIntent, REQUEST_GALLERY);

                        break;

                    case 1: //카메라
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Uri fileUri = getTempCameraUri();
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(intent,REQUEST_CAMERA);
                    break;

                }
            }
        });
        builder.create().show();
    }

    private void cropImage(Uri uri) {
        if (uri != null) {
            Intent photoPickerIntent = new Intent(
                    "com.android.camera.action.CROP", uri);
            photoPickerIntent.putExtra("aspectX", 1);
            photoPickerIntent.putExtra("aspectY", 1);
            photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                    getTempUri());
            photoPickerIntent.putExtra("outputFormat",
                    Bitmap.CompressFormat.JPEG.toString());
            startActivityForResult(photoPickerIntent, REQUEST_CROP);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case REQUEST_CAMERA:
                String imagePath = Environment.getExternalStorageDirectory() + "/"
                        + TEMP_CAMERA_FILE;
                try {
                    String url = MediaStore.Images.Media.insertImage(getContentResolver(), imagePath, "camera image", "original image");
                    Uri photouri = Uri.parse(url);
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.ORIENTATION, 90);
                    getContentResolver().update(photouri, values, null, null);
                    cropImage(photouri);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case REQUEST_GALLERY:
            case REQUEST_CROP:
                String filePath = Environment.getExternalStorageDirectory() + "/"
                        + TEMP_PHOTO_FILE;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                Bitmap selectedImage = BitmapFactory.decodeFile(filePath, options);
                baby_image.setImageBitmap(selectedImage);
                baby_image.setBackgroundColor(getResources().getColor(R.color.transparent));
                baby_image_file = new File(Environment.getExternalStorageDirectory() + "/" + TEMP_PHOTO_FILE);
                Log.e("BabyAddActivity", "앱솔루트패스"+baby_image_file.getAbsolutePath());
                break;
        }
    }


    /*************************start of camera**********************************/

    private Uri getTempCameraUri() {
        return Uri.fromFile(getCameraFile());
    }

    private File getCameraFile() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {

            File file = new File(Environment.getExternalStorageDirectory(),
                    TEMP_CAMERA_FILE);
            try {
                file.createNewFile();
            } catch (IOException e) {
            }

            return file;
        } else {

            return null;
        }
    }
    /*******************************end of camera****************************************/



    /******************************start of gallery*****************************************/
    private Uri getTempUri() {
        return Uri.fromFile(getTempFile());
    }

    private File getTempFile() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {

            File file = new File(Environment.getExternalStorageDirectory(),
                    TEMP_PHOTO_FILE);
            try {
                file.createNewFile();
            } catch (IOException e) {
            }

            return file;
        } else {

            return null;
        }
    }
    /******************************end of gallery*****************************************/


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}