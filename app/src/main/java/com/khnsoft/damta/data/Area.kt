package com.khnsoft.damta.data

import android.content.Context
import com.google.gson.JsonObject
import com.khnsoft.damta.utils.AreaType
import com.khnsoft.damta.utils.DatabaseHandler
import com.khnsoft.damta.utils.SDF
import java.lang.Exception
import java.util.*

class Area(
    var id: Int = -1,
    var name: String = "",
    var type: AreaType = AreaType.OPENED,
    var address: String = "",
    var x: Double = 0.0,
    var y: Double = 0.0,
    var ashtray: Boolean = false,
    var vent: Boolean = false,
    var bench: Boolean = false,
    var machine: Boolean = false,
    var density: Double = 0.0,
    var createdDate: Date? = null
) {
    companion object {
        fun getFromJson(jArea: JsonObject) : Area {
            return Area(
                id = jArea["id"].asInt,
                name = jArea["name"].asString,
                type = AreaType.valueOf(jArea["type"].asString),
                address = jArea["address"].asString,
                x = jArea["x"].asDouble,
                y = jArea["y"].asDouble,
                ashtray = jArea["ashtray"].asInt == 1,
                vent = jArea["vent"].asInt == 1,
                bench = jArea["bench"].asInt == 1,
                machine = jArea["machine"].asInt == 1,
                density = jArea["density"].asDouble,
                createdDate = SDF.dateBar.parse(jArea["created_date"].asString)
            )
        }

        fun getById(context: Context, id: Int) : Area? {
            val mHandler = DatabaseHandler.open(context)

            try {
                val sql = """
                    SELECT id, name, type, address, x, y, ashtray, vent, bench, machine, density, created_date 
                    FROM AREA_TB 
                    WHERE id=${id}
                """.trimIndent()

                val jResult = mHandler.read(sql)
                if (jResult.size() > 0) {
                    return getFromJson(jResult[0].asJsonObject)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                mHandler.close()
            }

            return null
        }

        fun searchByName(context: Context, name: String) : Array<Area> {
            val mHandler = DatabaseHandler.open(context)

            try {
                val sql = """
                    SELECT id, name, type, address, x, y, ashtray, vent, bench, machine, density, created_date
                    FROM AREA_TB
                    WHERE name LIKE "%${name}%"
                """.trimIndent()

                val jResult = mHandler.read(sql)
                val areas = Array(jResult.size()) { Area() }

                for (i in 0..jResult.size()-1) {
                    areas[i] = getFromJson(jResult[i].asJsonObject)
                }

                return areas
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                mHandler.close()
            }

            return arrayOf()
        }
    }

    fun add(context: Context) : Int? {
        val mHandler = DatabaseHandler.open(context)

        try {
            var sql = """
                    INSERT INTO AREA_TB (name, type, address, x, y, ashtray, vent, bench, machine, density, created_date)
                    VALUES (
                        "${name}",
                        "${type.name}",
                        "${address}",
                        "${x}",
                        "${y}",
                        ${if (ashtray) 1 else 0},
                        ${if (vent) 1 else 0},
                        ${if (bench) 1 else 0},
                        ${if (machine) 1 else 0},
                        ${density},
                        "${SDF.dateBar.format(Date())}"
                    )
                """.trimIndent()

            mHandler.write(sql)

            sql = """
                SELECT MAX(id) AS id FROM AREA_TB
            """.trimIndent()

            val jResult = mHandler.read(sql)

            if (jResult.size() > 0) {
                return jResult[0].asJsonObject["id"].asInt
            }

            return null
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            mHandler.close()
        }

        return null
    }

    fun save(context: Context) : Boolean {
        val mHandler = DatabaseHandler.open(context)

        try {
            val sql = """
                    UPDATE AREA_TB SET
                    name="${name},"
                    type="${type.name}",
                    address="${address}",
                    x=${x},
                    y=${y},
                    ashtray=${if (ashtray) 1 else 0},
                    vent=${if (vent) 1 else 0},
                    bench=${if (bench) 1 else 0},
                    machine=${if (machine) 1 else 0},
                    density=${density}
                    WHERE id=${id}
                """.trimIndent()

            mHandler.write(sql)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            mHandler.close()
        }

        return false
    }
}