package com.example.gamexo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultDialog extends Dialog {

    private final String message;
    private final PlayingField playingField;

    public ResultDialog(@NonNull Context context, String message, PlayingField playingField) {
        super(context);
        this.message = message;
        this.playingField = playingField;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_dialog);

        TextView messageText = findViewById(R.id.messageText);
        Button startAganeButton = findViewById(R.id.startAgainButton);

        messageText.setText(message);

        startAganeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playingField.restartMatch();
                dismiss();  // закрытие окна
            }
        });
    }
}