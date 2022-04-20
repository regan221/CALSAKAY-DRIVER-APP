package com.example.calsakay_driver;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUp5Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUp5Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignUp5Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Signup5.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUp5Fragment newInstance(String param1, String param2) {
        SignUp5Fragment fragment = new SignUp5Fragment();
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

    private Button btn_signup5, btn_signup5_back;
    private EditText et_password, et_username, et_password_confirm;
    private DatabaseAccess dbAccess;
//    private Bundle extras;

    private String str_username, str_password, str_confirm_password;
    // test string
    private String account_status = "0", role = "1", date_joined = "0";
    private Bundle bundle;
    Signup signup;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up5, container, false);
        bundle = this.getArguments();
        signup = (Signup) getActivity();

        et_username = view.findViewById(R.id.et_username);
        et_password = view.findViewById(R.id.et_password);
        et_password_confirm = view.findViewById(R.id.et_confirmPass);


        dbAccess = new DatabaseAccess(getActivity());

        btn_signup5 = view.findViewById(R.id.btn_signup5);
        btn_signup5_back = view.findViewById(R.id.btn_signup5_back);

        btn_signup5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signup.setUsername(et_username.getText().toString());
                signup.setPassword(et_password.getText().toString());

                if (et_username.getText().toString().matches("")){
                    Toast.makeText(getActivity(), "Username is required", Toast.LENGTH_SHORT).show();
                }else if(et_password.getText().toString().matches("")){
                    Toast.makeText(getActivity(), "Password is required", Toast.LENGTH_SHORT).show();
                }else if(et_password_confirm.getText().toString().matches("")){
                    Toast.makeText(getActivity(), "Password confirmation is required", Toast.LENGTH_SHORT).show();
                }else if(!et_password.getText().toString().matches(et_password_confirm.getText().toString())){
                    Toast.makeText(getActivity(), "Password field and confirmation password is not same.", Toast.LENGTH_SHORT).show();
                }else{
//                    insertData();
                    SignUpFinalStepFragment signUpFinalStepFragment = new SignUpFinalStepFragment();
                    signUpFinalStepFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.signupLayout, signUpFinalStepFragment).commit();
                }

            }
        });

        btn_signup5_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUp4Fragment signUp4Fragment = new SignUp4Fragment();
                signUp4Fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.signupLayout, signUp4Fragment).commit();
            }
        });
        return view;
    }

    public void insertData(){
        ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Setting up your account...");
        pd.show();
        try {
            dbAccess.executeNonQuery("INSERT INTO calsakay_tbl_users (" +
                    "first_name, last_name, gender, birthday, mobile_number, address, medical_job, company_name, company_address, " +
                    "company_number, front_image_name, back_image_name, email, user_image, username, password, account_status, role)" +
                    "VALUES ('" +
                    signup.getFname() + "','" +
                    signup.getLname() + "','" +
                    signup.getGender() + "','" +
                    signup.getBirthday() + "','" +
                    signup.getContact_num() + "','" +
                    signup.getAddress() + "','" +
                    signup.getMedical_job() + "','" +
                    signup.getCompany_name() + "','" +
                    signup.getCompany_address() + "','" +
                    signup.getCompany_number() + "','" +
                    signup.getFrontImageId() + "','" +
                    signup.getBackImageId() + "','" +
                    signup.getEmail() + "','" +
                    signup.getProfile_picture() + "','" +
                    signup.getUsername() + "','" +
                    signup.getPassword() + "','0','1')");
            pd.dismiss();
        }catch (Exception e){
            Log.e("CATCH ERROR: ", e.getMessage());
            pd.dismiss();
            Toast.makeText(getActivity(), "Failed to create your account, please try again later or contact the admin.", Toast.LENGTH_SHORT).show();

        }
    }
}