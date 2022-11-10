package io.kosl.execution.analysis

import io.kosl.io.Path

class ExecutionAnalysis(
  val requiredFilePaths: List<Path>,
  val requiredDirectoryPaths: List<Path>,
  val requiredEntityPaths: List<Path>,
  val requiredCommandNames: List<String>,
  val requiredSubCommandPatterns: List<List<String>>
)
