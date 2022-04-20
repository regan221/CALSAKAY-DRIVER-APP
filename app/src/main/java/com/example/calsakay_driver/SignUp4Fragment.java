package com.example.calsakay_driver;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUp4Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUp4Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignUp4Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUp4Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUp4Fragment newInstance(String param1, String param2) {
        SignUp4Fragment fragment = new SignUp4Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private Button btn_signup4, btn_signup4_back;
    private ImageView iv_profile_pic;
    private String profileImage = "";
    private String currentPhotoPath;
    Signup signup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up4, container, false);
        Bundle bundle = new Bundle();
        signup = (Signup) getActivity();

        btn_signup4 = view.findViewById(R.id.btn_signup4);
        btn_signup4_back = view.findViewById(R.id.btn_signup4_back);
        iv_profile_pic = view.findViewById(R.id.iv_profilPic);

        iv_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filename = "photo";
                File storageDirectory = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

                try {
                    File imageFile = File.createTempFile(filename, ".jpg", storageDirectory);
                    currentPhotoPath = imageFile.getAbsolutePath();

                    Uri imageUri = FileProvider.getUriForFile(getActivity(), "com.example.calsakay.fileprovider",imageFile);

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, 1);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_signup4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(profileImage.matches("")){
                    Toast.makeText(getActivity(), "Profile image is required", Toast.LENGTH_SHORT).show();
                }else{
                    SignUp5Fragment signUp5Fragment = new SignUp5Fragment();
                    signUp5Fragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.signupLayout, signUp5Fragment).commit();
                }

            }
        });

        btn_signup4_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUp4Fragment signUp4Fragment = new SignUp4Fragment();
                signUp4Fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.signupLayout, signUp4Fragment).commit();
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);
            byte[] bytes = stream.toByteArray();

            profileImage = Base64.encodeToString(bytes, Base64.DEFAULT);
            iv_profile_pic.setImageBitmap(bitmap);
//            Log.d("Value3", profileImage);
            signup.setProfile_picture(profileImage);
        }
    }
}