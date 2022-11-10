package io.kosl.execution.parameter

import io.kosl.io.Path
import kotlin.io.path.pathString
import kotlin.io.path.relativeTo

class RelativeFilePath(val path: Path): ExecutionParameter() {
  override fun toCommandArgument(localContextDirectory: Path): String {
    return path.relativeTo(localContextDirectory).pathString
  }
}
