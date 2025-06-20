package com.example.greycare.utils

import android.content.Context
import android.os.Environment
import android.widget.Toast
import com.example.greycare.models.Vitals
import com.opencsv.CSVWriter
import java.io.File
import java.io.FileWriter

fun exportVitalsToCSV(context: Context, vitalsList: List<Vitals>) {
    try {
        val csvDir = File(context.getExternalFilesDir(null), "exports")
        if (!csvDir.exists()) csvDir.mkdirs()

        val csvFile = File(csvDir, "vitals_export.csv")
        val writer = CSVWriter(FileWriter(csvFile))

        val header = arrayOf("Heart Rate", "Blood Pressure", "Glucose Level", "Timestamp")
        writer.writeNext(header)

        for (v in vitalsList) {
            val row = arrayOf(v.heartRate, v.bloodPressure, v.glucoseLevel, v.timestamp.toString())
            writer.writeNext(row)
        }

        writer.close()

        Toast.makeText(context, "CSV exported to ${csvFile.absolutePath}", Toast.LENGTH_LONG).show()

    } catch (e: Exception) {
        Toast.makeText(context, "Export failed: ${e.message}", Toast.LENGTH_LONG).show()
    }
}
