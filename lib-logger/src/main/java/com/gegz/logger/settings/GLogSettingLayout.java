package com.gegz.logger.settings;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.gegz.logger.R;
import com.gegz.logger.core.constants.GLogTypes;
import com.gegz.logger.core.entity.GLogEntity;

/**
 * 日志框架灵活配置布局
 */
public class GLogSettingLayout extends LinearLayout {

    private final String[] levelArray = {"V", "D", "I", "W", "E", "WTF"};

    /**
     * 日志类类名文本框
     */
    private TextView classNameText;
    /**
     * 日志模块名称编辑框
     */
    private EditText moduleNameEdit;
    /**
     * 日志类型编辑框
     */
    private EditText typeNameEdit;
    /**
     * 日志默认级别下拉选
     */
    private Spinner defaultLevelSpinner;
    /**
     * Json日志级别下拉选
     */
    private Spinner jsonLevelSpinner;
    /**
     * 是否解析json复选框
     */
    private CheckBox formatJsonCheck;
    /**
     * 任务栈数量编辑框
     */
    private EditText traceCountEdit;
    /**
     * 强制显示复选框
     */
    private CheckBox forceShowCheck;
    /**
     * 显示分割线复选框
     */
    private CheckBox showDivideCheck;
    /**
     * 显示多块复选框
     */
    private CheckBox showAllCheck;
    /**
     * 最大日志块数输入框
     */
    private EditText maxLengthEdit;

    /**
     * 控件的上下文对象
     */
    private Context context;
    /**
     * 日志配置对象
     */
    private GLogEntity entity;

    /**
     * 日志模块标识
     */
    private String moduleName = "";
    /**
     * 日志类型标识
     */
    private String typeName = "";
    /**
     * 默认日志级别
     */
    private int defaultLevel = GLogTypes.V;
    /**
     * Json日志和Xml日志级别
     */
    private int jsonLevel = GLogTypes.V;
    /**
     * 显示的任务栈数量
     */
    private int traceCount = 0;
    /**
     * 最大日志块数（可以显示多少个MAX_LENGTH的长度）
     */
    private int maxLengthCount = 1;

    /**
     * 检查日志配置是否合法的方法
     */
    public boolean checkSetting() {
        if (TextUtils.isEmpty(moduleName) || moduleName.length() > 8) {
            Toast.makeText(context, "请输入1 - 8位ModuleName", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(typeName) || typeName.length() > 8) {
            Toast.makeText(context, "请输入1 - 8位TypeName", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (traceCount < 0 || traceCount > 100) {
            Toast.makeText(context, "请输入1 - 100tranceCount数值", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (maxLengthCount < 0 || maxLengthCount > 100) {
            Toast.makeText(context, "请输入1 - 100maxLengthCount数值", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 保存设置的方法
     */
    public void saveEntity() {
        entity.initModuleName(moduleName);
        entity.initTypeName(typeName);
        entity.initDefaultLevel(defaultLevel);
        entity.initJsonLevel(jsonLevel);
        entity.initFormatJson(formatJsonCheck.isChecked());
        entity.initTraceCount(traceCount);
        entity.initForceShow(forceShowCheck.isChecked());
        entity.initShowDivide(showDivideCheck.isChecked());
        entity.initShowAll(showAllCheck.isChecked());
        entity.initMaxLengthCount(maxLengthCount);
    }

    /**
     * 设置日志初始状态的方法
     */
    public void initEntity(GLogEntity entity) {
        this.entity = entity;

        classNameText.setText(entity.getClassName());

        moduleName = entity.getModuleName();
        moduleNameEdit.setText(entity.getModuleName());
        moduleNameEdit.addTextChangedListener(new GLogSettingTextWatcher(GLogSettingTextWatcher.TYPE_TEXT_WATCHER_MODULE_NAME));


        typeName = entity.getTypeName();
        typeNameEdit.setText(entity.getTypeName());
        typeNameEdit.addTextChangedListener(new GLogSettingTextWatcher(GLogSettingTextWatcher.TYPE_TEXT_WATCHER_TYPE_NAME));

        defaultLevel = entity.getDefaultLevel();
        initLevelSpinner(defaultLevelSpinner, entity, GLogSettingSelectedListener.TYPE_SPINNER_DEFAULT_LEVEL);

        jsonLevel = entity.getJsonLevel();
        initLevelSpinner(jsonLevelSpinner, entity, GLogSettingSelectedListener.TYPE_SPINNER_JSON_LEVEL);

        formatJsonCheck.setChecked(entity.isFormatJson());

        traceCount = entity.getTraceCount();
        traceCountEdit.setText(String.valueOf(entity.getTraceCount()));
        traceCountEdit.addTextChangedListener(new GLogSettingTextWatcher(GLogSettingTextWatcher.TYPE_TEXT_WATCHER_TRACE_COUNT));

        forceShowCheck.setChecked(entity.isForceShow());

        showDivideCheck.setChecked(entity.isShowDivide());

        showAllCheck.setChecked(entity.isShowAll());

        maxLengthCount = entity.getMaxLengthCount();
        maxLengthEdit.setText(String.valueOf(entity.getMaxLengthCount()));
        maxLengthEdit.addTextChangedListener(new GLogSettingTextWatcher(GLogSettingTextWatcher.TYPE_TEXT_WATCHER_MAX_LENGTH));
    }

    /**
     * 初始化默认级别下拉选的方法
     */
    private void initLevelSpinner(Spinner spinner, GLogEntity entity, int levelType) {
        ArrayAdapter<String> starAdapter = new ArrayAdapter<>(context, R.layout.item_glog_setting_spinner, levelArray);
        starAdapter.setDropDownViewResource(R.layout.item_glog_setting_spinner_dropdown);
        spinner.setAdapter(starAdapter);
        if (levelType == GLogSettingSelectedListener.TYPE_SPINNER_DEFAULT_LEVEL) {
            switch (entity.getDefaultLevel()) {
                case GLogTypes.V:
                default:
                    spinner.setSelection(0);
                    break;
                case GLogTypes.D:
                    spinner.setSelection(1);
                    break;
                case GLogTypes.I:
                    spinner.setSelection(2);
                    break;
                case GLogTypes.W:
                    spinner.setSelection(3);
                    break;
                case GLogTypes.E:
                    spinner.setSelection(4);
                    break;
                case GLogTypes.WTF:
                    spinner.setSelection(5);
                    break;
            }
        } else {
            switch (entity.getJsonLevel()) {
                case GLogTypes.V:
                default:
                    spinner.setSelection(0);
                    break;
                case GLogTypes.D:
                    spinner.setSelection(1);
                    break;
                case GLogTypes.I:
                    spinner.setSelection(2);
                    break;
                case GLogTypes.W:
                    spinner.setSelection(3);
                    break;
                case GLogTypes.E:
                    spinner.setSelection(4);
                    break;
                case GLogTypes.WTF:
                    spinner.setSelection(5);
                    break;
            }
        }
        spinner.setOnItemSelectedListener(new GLogSettingSelectedListener(levelType));
    }

    /**
     * 日志级别下拉选选中监听
     */
    private class GLogSettingSelectedListener implements AdapterView.OnItemSelectedListener {

        private static final int TYPE_SPINNER_DEFAULT_LEVEL = 11510;
        private static final int TYPE_SPINNER_JSON_LEVEL = 11511;

        private final int levelType;

        private GLogSettingSelectedListener(int levelType) {
            this.levelType = levelType;
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (levelType == TYPE_SPINNER_DEFAULT_LEVEL) {
                switch (i) {
                    case 0:
                    default:
                        defaultLevel = GLogTypes.V;
                        break;
                    case 1:
                        defaultLevel = GLogTypes.D;
                        break;
                    case 2:
                        defaultLevel = GLogTypes.I;
                        break;
                    case 3:
                        defaultLevel = GLogTypes.W;
                        break;
                    case 4:
                        defaultLevel = GLogTypes.E;
                        break;
                    case 5:
                        defaultLevel = GLogTypes.WTF;
                        break;
                }
            } else {
                switch (i) {
                    case 0:
                    default:
                        jsonLevel = GLogTypes.V;
                        break;
                    case 1:
                        jsonLevel = GLogTypes.D;
                        break;
                    case 2:
                        jsonLevel = GLogTypes.I;
                        break;
                    case 3:
                        jsonLevel = GLogTypes.W;
                        break;
                    case 4:
                        jsonLevel = GLogTypes.E;
                        break;
                    case 5:
                        jsonLevel = GLogTypes.WTF;
                        break;
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    /**
     * 输入框变化监听
     */
    private class GLogSettingTextWatcher implements TextWatcher {

        private static final int TYPE_TEXT_WATCHER_MODULE_NAME = 15230;
        private static final int TYPE_TEXT_WATCHER_TYPE_NAME = 15231;
        private static final int TYPE_TEXT_WATCHER_TRACE_COUNT = 15232;
        private static final int TYPE_TEXT_WATCHER_MAX_LENGTH = 15233;

        private final int textType;

        private GLogSettingTextWatcher(int textType) {
            this.textType = textType;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            switch (textType) {
                case TYPE_TEXT_WATCHER_MODULE_NAME:
                    moduleName = charSequence.toString();
                default:
                    break;
                case TYPE_TEXT_WATCHER_TYPE_NAME:
                    typeName = charSequence.toString();
                    break;
                case TYPE_TEXT_WATCHER_TRACE_COUNT:
                    try {
                        traceCount = Integer.parseInt(charSequence.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case TYPE_TEXT_WATCHER_MAX_LENGTH:
                    try {
                        maxLengthCount = Integer.parseInt(charSequence.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    /**
     * 初始化自定义设置布局的方法
     */
    private void initLayout(Context context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_glog_setting, this);
        // 获取控件对象
        classNameText = findViewById(R.id.tv_glog_class_name);
        moduleNameEdit = findViewById(R.id.et_glog_module_name);
        typeNameEdit = findViewById(R.id.et_glog_type_name);
        defaultLevelSpinner = findViewById(R.id.sp_glog_default_level);
        jsonLevelSpinner = findViewById(R.id.sp_glog_json_level);
        formatJsonCheck = findViewById(R.id.cb_glog_format_json);
        traceCountEdit = findViewById(R.id.et_glog_trace_count);
        forceShowCheck = findViewById(R.id.cb_glog_force_show);
        showDivideCheck = findViewById(R.id.cb_glog_show_divide);
        showAllCheck = findViewById(R.id.cb_glog_show_all);
        maxLengthEdit = findViewById(R.id.et_glog_max_length);
    }

    /**
     * 构造方法
     */
    public GLogSettingLayout(Context context) {
        super(context);
        initLayout(context);
    }

    public GLogSettingLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
    }

    public GLogSettingLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);
    }
}
