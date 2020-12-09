package com.martitech.multilinktextview

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatTextView
import java.util.regex.Matcher
import java.util.regex.Pattern

class MultiLinkTextView : AppCompatTextView {
    private var onLinkClickListener: OnLinkClickListener? = null
    private var defaultColor: Int = Color.rgb(2, 20, 154)
    private var linkUnderLine: Boolean = false
    private var linkHighLightColor: Int = Color.argb(35, 128, 128, 128)
    private var linkTag: String = "l"
    private var originText: String = ""

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        init(context, attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ){
        init(context,attributeSet)
    }


    private fun init(context: Context, attributeSet: AttributeSet?) {
        originText = text.toString()
        val a =
            context.theme.obtainStyledAttributes(attributeSet, R.styleable.MultiLinkTextView, 0, 0)
        try {
            defaultColor = a.getColor(R.styleable.MultiLinkTextView_linkColor, defaultColor)
            linkUnderLine = a.getBoolean(R.styleable.MultiLinkTextView_linkUnderLine, linkUnderLine)
            linkHighLightColor =
                a.getColor(R.styleable.MultiLinkTextView_linkHighLightColor, linkHighLightColor)
            a.getString(R.styleable.MultiLinkTextView_linkTag)?.let {
                if (it.isNotBlank()) linkTag = it
            }
        } finally {
            a.recycle()
        }
        initView()
    }

    fun addLinkClickListener(onLinkClickListener: OnLinkClickListener) {
        this.onLinkClickListener = onLinkClickListener
        initView()
    }

    fun linkColor(@ColorRes linkColor: Int) {
        this.defaultColor = linkColor
        initView()
    }

    fun setLinkTag(linkTag: String) {
        this.linkTag = linkTag
        initView()
    }


    private fun initView() {
        val regexList = Regex("<$linkTag>(.+?)</$linkTag>")
        val matchers: MutableList<Matcher> = mutableListOf()
        val matches = regexList.findAll(originText)
        matches.forEach {
            matchers.add(Pattern.compile(it.groupValues[1]).matcher(originText))
        }
        val spannedText = originText.replace(Regex("<[^>]+>\\s+(?=<)|<[^>]+>"), "")
        val spannable = SpannableString(spannedText)
        for (i in 0 until matchers.size) {
            if (matchers[i].find()) {
                val link = Pattern.compile(matchers[i].pattern().toString()).matcher(spannedText)
                if (link.find())
                    spannable.setSpan(
                        ClickableSpanText(i, onLinkClickListener).apply { underlineSpan=linkUnderLine },
                        link.start(),
                        link.end(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
            }
        }
        setText(spannable, BufferType.SPANNABLE)
        movementMethod = LinkMovementMethod()
        highlightColor = linkHighLightColor
        setLinkTextColor(defaultColor)

    }

}