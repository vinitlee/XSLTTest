package com.vinitlee.bose.xslttest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.os.Environment.getExternalStorageDirectory
import android.R.raw
import android.os.Environment
import android.text.TextWatcher
import android.util.Log
import java.io.File
import java.io.StringReader
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource
import java.io.StringWriter
import android.text.Editable






class MainActivity : AppCompatActivity() {

    lateinit var inText : EditText
    lateinit var xslText : EditText
    lateinit var outText : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inText = findViewById(R.id.inputText)
        xslText = findViewById(R.id.xslText)
        outText = findViewById(R.id.outputText)

        inText.setText("<item>this is an item</item>")
        xslText.setText("happy")

        findViewById<Button>(R.id.button).setOnClickListener { buildText() }

        val mWatcher = object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                buildText()
//                if (p0 != null) {
//                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        }

        inText.addTextChangedListener(mWatcher)
        xslText.addTextChangedListener(mWatcher)
    }

    private fun buildText() {
        val transFact = TransformerFactory.newInstance()
        val outWriter = StringWriter()

        val xmlSource = StreamSource(StringReader(inText.text.toString()))
        val xsltSource = StreamSource(StringReader(xslText.text.toString()))

        try {
            val trans = transFact.newTransformer(xsltSource)
            val result = StreamResult(outWriter)
            trans.transform(xmlSource, result)
            val finalString = outWriter.buffer.toString()
            outText.setText(finalString)
        }
        catch (e: NullPointerException) {
            outText.setText("")
        }

    }
}
