package io.kosl.config

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

open class KoslModel<T>(private val serializer: KSerializer<T>) {
  private val json = Json {}

  fun loadFromPath(path: Path): T {
    return json.decodeFromString(serializer, Files.readString(path, StandardCharsets.UTF_8))
  }

  fun loadFromPath(path: String): T {
    return loadFromPath(Paths.get(path))
  }

  fun loadFromString(content: String): T{
    return json.decodeFromString(serializer, content)
  }
}
