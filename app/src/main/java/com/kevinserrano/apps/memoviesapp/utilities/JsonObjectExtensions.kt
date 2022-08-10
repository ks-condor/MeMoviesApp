@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.kevinserrano.apps.memoviesapp.utilities

import android.app.Activity
import android.content.Intent
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject

fun getResponse(jsonObject: JsonObject): String {
    var jsonElement: JsonElement = jsonObject
    if(jsonObject.get("response")!=null) {
        if(!jsonObject.get("response").isJsonNull){
            jsonElement = jsonObject.get("response")
        }
    }
    if(jsonObject.get("results")!=null) {
        if(!jsonObject.get("results").isJsonNull){
            jsonElement = jsonObject.get("results").asJsonArray[0].asJsonObject
        }
    }
    if(jsonObject.get("msg")!=null) {
        if(!jsonObject.get("msg").isJsonNull){
            jsonElement = jsonObject.get("msg")
        }
    }
    if(jsonObject.get("error_description")!=null) {
        if(!jsonObject.get("error_description").isJsonNull){
            jsonElement = jsonObject.get("error_description")
        }
    }
    return jsonElement.toString()
}

fun <T> fromJson(json: String, classOfT: Class<T>): T {
    return try {
        Gson().fromJson(if(json.isEmpty()) "{}" else json, classOfT)
    } catch (error: Exception) {
        Gson().fromJson("{}", classOfT)
    }
}

fun <T> fromJson(jsonObject: JsonObject, classOfT: Class<T>): T = fromJson(getResponse(jsonObject), classOfT)

fun <T> fromJson(jsonObject: JsonObject, clazz: Class<Array<T>>): List<T> = Gson().fromJson(getResponse(jsonObject), clazz).toList()

fun <T> fromJson(jsonObject: String, clazz: Class<Array<T>>): List<T> = Gson().fromJson(jsonObject, clazz).toList()

fun <T> getExtraCode(classOfT: Class<T>): String {
    classOfT.methods.forEach {
        if (it.name.contains("EXTRA")) {
            return it.invoke(null).toString()
        }
    }
    return classOfT.name
}

fun Intent.put(any: Any) {
    this.putExtra(any::class.java.name, toJson(any))
}
fun toJson(any: Any): String = Gson().toJson(any)

fun <T> Activity.get(classOfT: Class<T>): T {
    val json = this.intent.getStringExtra(classOfT.name) ?: "{}"
    return fromJson(json, classOfT)
}

