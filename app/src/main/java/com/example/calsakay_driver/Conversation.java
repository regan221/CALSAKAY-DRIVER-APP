package com.example.calsakay_driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dd.CircularProgressButton;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Conversation extends AppCompatActivity implements DatabaseAccessCallback {

    List<ConversationModel> convo = new ArrayList<>();
    RecyclerView rvConvo;
    int chatMateId, userId;
    Messages messageData;
    String threadName, chatmateImage;
    ConversationRecViewAdapter convoAdapter = new ConversationRecViewAdapter();
    CircularProgressButton sendButton;
    EditText etMessage;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d HH:mm:ss");
    ImageView chatImage;
    TextView chatName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        DatabaseAccess dbAccessForRefreshing = new DatabaseAccess(this);
        DatabaseAccess dbAccessForSending = new DatabaseAccess(this);


        this.rvConvo = findViewById(R.id.rvConversation);
        this.sendButton = findViewById(R.id.btMessageSend);
        this.etMessage = findViewById(R.id.etTypeMEssage);
        this.chatImage = findViewById(R.id.ivChatmateImage);
        this.chatName = findViewById(R.id.tvConvoName);
        getSupportActionBar().hide();

        this.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageContent = etMessage.getText().toString(), messageTimeStamp = dateFormat.format(new Date()), messageType = "outgoing";
                if(!TextUtils.isEmpty(messageContent)){
                    convo.add(new ConversationModel(messageContent, messageTimeStamp, messageType));
                    convoAdapter.notifyItemInserted(convo.size() - 1);
                    rvConvo.scrollToPosition(convo.size() - 1);
                    dbAccessForSending.executeNonQuery("INSERT INTO `messages` " +
                            "(`message_id`, `sender`, `reciever`, `message`, `read`, `time`) " +
                            "VALUES (NULL, " + userId + ", " + chatMateId + ", '" + messageContent + "', " + 1 + ", CURRENT_TIMESTAMP)");
                    etMessage.setText("");
                }
            }
        });

        Intent intent = getIntent();
        this.messageData = (Messages) intent.getSerializableExtra("messageData");
        this.chatMateId = messageData.getChatMateId();
        this.userId = messageData.getPassengerId();
        this.chatName.setText(messageData.getThreadName());


// SELECT CONCAT(calsakay_tbl_users.first_name, ' ', calsakay_tbl_users.last_name) AS threadName, IF(messages.sender = 62, 'outgoing', 'ingoing') AS messageType, messages.sender, messages.reciever, messages.message_id, messages.message, messages.read, messages.time, messages.read FROM `messages` JOIN calsakay_tbl_users ON IF(messages.sender = 62, messages.reciever = calsakay_tbl_users.id, messages.sender = calsakay_tbl_users.id) WHERE (`reciever` = 62 AND `sender` = 78) OR (`reciever` = 78 AND `sender` = 62)

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 5000);
                dbAccessForRefreshing.executeQuery("SELECT CONCAT(calsakay_tbl_users.first_name, ' ', " +
                        "calsakay_tbl_users.last_name) AS threadName, " +
                        "IF(messages.sender = " + userId + ", 'outgoing', 'ingoing') AS messageType, " +
                        "messages.sender, messages.reciever, messages.message_id, messages.message, " +
                        "messages.read, messages.time, messages.read, calsakay_tbl_users.user_image FROM `messages` JOIN calsakay_tbl_users " +
                        "ON IF(messages.sender = " + userId + ", messages.reciever = calsakay_tbl_users.id, messages.sender = calsakay_tbl_users.id) " +
                        "WHERE (`reciever` = " + userId + " AND `sender` = " + chatMateId + ") OR (`reciever` = " + chatMateId + " AND `sender` = " + userId + ") ORDER BY messages.message_id");
            }
        }, 3000);


    }

    @Override
    public void QueryResponse(List<String[]> data) {
        if(data != null){
//            this.chatmateImage = data.get(0)[9];
//            InputStream stream = new ByteArrayInputStream(Base64.decode(chatmateImage.getBytes(), Base64.DEFAULT));
//            Bitmap chatImageBitmap = BitmapFactory.decodeStream(stream);
//            this.chatImage.setImageBitmap(chatImageBitmap);

            if(this.convo.size() == 0){
                for (String[] row : data) {
                    this.convo.add(new ConversationModel(row[5],row[7], row[1]));
                }

                this.convoAdapter.setConvo(this.convo);
                this.rvConvo.setAdapter(this.convoAdapter);
                LinearLayoutManager ll = new LinearLayoutManager(this);
                ll.setStackFromEnd(true);
                this.rvConvo.setLayoutManager(ll);
            } else if(convo.size() < data.size()){
                this.convo.add(new ConversationModel(data.get(data.size() - 1)[5],data.get(data.size() - 1)[7], data.get(data.size() - 1)[1]));
                this.convoAdapter.notifyItemInserted(this.convo.size() - 1);
                this.rvConvo.scrollToPosition(this.convo.size() - 1);
            }
        }
    }
}