package com.example.ai_prompt;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.Arrays;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.CheckBox;
import android.widget.Toast;
import android.content.Intent;

public class EditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editor);


        Spinner spTaskType = findViewById(R.id.spTaskType);

        String[] taskTypes = {
                "学习辅导",
                "代码解释",
                "文章润色",
                "内容总结"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                taskTypes
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spTaskType.setAdapter(adapter);

        EditText etQuestion = findViewById(R.id.etQuestion);
        RadioGroup rgStyle = findViewById(R.id.rgStyle);
        CheckBox cbExample = findViewById(R.id.cbExample);
        CheckBox cbSteps = findViewById(R.id.cbSteps);
        CheckBox cbTable = findViewById(R.id.cbTable);
        Button btnGenerate = findViewById(R.id.btnGenerate);

        btnGenerate.setOnClickListener(v -> {

            // 1. 获取用户输入的问题
            String question = etQuestion.getText().toString().trim();

            if (question.isEmpty()) {
                etQuestion.setError("请先输入你的需求");
                etQuestion.requestFocus();
                return;
            }

            // 2. 获取任务类型
            String taskType = spTaskType.getSelectedItem().toString();

            // 3. 获取回答风格
            String style;

            int checkedId = rgStyle.getCheckedRadioButtonId();

            if (checkedId == R.id.rbConcise) {
                style = "请用简洁明了的语言回答，突出重点。";
            } else if (checkedId == R.id.rbProfessional) {
                style = "请使用专业、严谨、正式的语言回答。";
            } else {
                style = "请详细讲解，使用通俗易懂的语言，适合初学者理解。";
            }

            // 4. 开始构建提示词
            StringBuilder prompt = new StringBuilder();

            switch (taskType) {
                case "学习辅导":
                    prompt.append("请你担任一名耐心的学习辅导老师。\n");
                    break;
                case "代码解释":
                    prompt.append("请你担任一名经验丰富的程序设计教师。\n");
                    break;
                case "文章润色":
                    prompt.append("请你担任一名专业的文字编辑。\n");
                    break;
                case "内容总结":
                    prompt.append("请你担任一名善于提炼信息的总结专家。\n");
                    break;
            }

            prompt.append("\n我的需求是：\n");
            prompt.append(question);
            prompt.append("\n\n回答风格：\n");
            prompt.append(style);

            // 5. 根据多选框添加附加要求
            if (cbExample.isChecked()) {
                prompt.append("\n请提供具体例子帮助理解。");
            }

            if (cbSteps.isChecked()) {
                prompt.append("\n请按照清晰的步骤组织回答。");
            }

            if (cbTable.isChecked()) {
                prompt.append("\n适合比较或归纳的信息请使用表格整理。");
            }

            prompt.append("\n请确保最终回答准确、清晰、有条理。");

            // 6. 临时显示生成结果
            Toast.makeText(
                    EditorActivity.this,
                    "提示词生成成功！",
                    Toast.LENGTH_SHORT
            ).show();

            Intent intent = new Intent(EditorActivity.this, ResultActivity.class);
            intent.putExtra("prompt", prompt.toString());
            startActivity(intent);
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}