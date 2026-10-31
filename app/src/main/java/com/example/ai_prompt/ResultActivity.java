
package com.example.ai_prompt;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // 获取页面控件
        TextView tvResult = findViewById(R.id.tvResult);
        Button btnCopy = findViewById(R.id.btnCopy);
        Button btnBack = findViewById(R.id.btnBack);
        ListView lvTemplates = findViewById(R.id.lvTemplates);

        // 1. 接收创作页面传来的提示词
        String prompt = getIntent().getStringExtra("prompt");
        if (prompt == null || prompt.isEmpty()) {
            prompt = "暂无提示词，请返回创作页面生成。";
        }
        tvResult.setText(prompt);

        // 2. 复制提示词
        btnCopy.setOnClickListener(v -> {
            String text = tvResult.getText().toString();

            ClipboardManager clipboard =
                    (ClipboardManager) getSystemService(
                            Context.CLIPBOARD_SERVICE);

            if (clipboard != null) {
                ClipData clip = ClipData.newPlainText(
                        "PromptCraft 提示词", text);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this,
                        "已复制到剪贴板",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // 3. 内置灵感模板
        String[] templateNames = {
                "📚 学习知识点讲解",
                "💻 Java 代码分析",
                "✍️ 文章优化润色",
                "📝 长文章重点总结"
        };

        String[] templateContents = {
                "请作为一名耐心的教师，用通俗的语言解释以下知识点，并提供具体例子和练习题：\n[填写知识点]",
                "请作为一名 Java 教师，逐行解释以下代码，说明运行流程、输出结果和常见错误：\n[粘贴代码]",
                "请作为一名专业编辑，润色下面的文章，改善表达、语法和逻辑，同时保留原意：\n[粘贴文章]",
                "请阅读以下文章，提取核心观点，并按照条目总结，最后给出简短结论：\n[粘贴文章]"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                templateNames
        );

        lvTemplates.setAdapter(adapter);

        // 点击模板后显示示例内容
        lvTemplates.setOnItemClickListener(
                (parent, view, position, id) -> {
                    tvResult.setText(templateContents[position]);
                }
        );

        // 4. 返回创作页面
        btnBack.setOnClickListener(v -> finish());
    }
}
