package io.kosl.context

import io.kosl.build.BuildEngine
import io.kosl.execution.ExecutionEngine
import io.kosl.execution.ExecutionJob
import io.kosl.execution.parameter.ExecutionParameter
import io.kosl.io.Path
import io.kosl.state.WorkspaceState
import kotlin.io.path.pathString

class KoslContext {
  lateinit var workspaceDirectoryPath: Path
  lateinit var workspaceState: WorkspaceState
  lateinit var buildEngine: BuildEngine
  lateinit var tag: String
  lateinit var executionEngine: ExecutionEngine

  var overrideImagePrefix: String? = null
  var isDryRun: Boolean = false
  var shouldAnalyzeExecution: Boolean = false

  fun logCommandExecution(job: ExecutionJob, run: CommandRunState = CommandRunState.Automatic) {
    val prefix = when (run) {
      CommandRunState.Automatic -> if (isDryRun) {
        "(dry) "
      } else {
        ""
      }

      CommandRunState.DryRun -> "(dry) "
      CommandRunState.Executed -> ""
    }

    println("${prefix}$ ${job.expandCommandArguments().joinToString(" ")}")
  }

  fun executeInteractiveProcess(command: List<ExecutionParameter>, run: CommandRunState = CommandRunState.Automatic) {
    val job = ExecutionJob(command, workspaceDirectoryPath)

    logCommandExecution(job, run = run)
    if ((run == CommandRunState.Automatic && isDryRun) || run == CommandRunState.DryRun) {
      return
    }

    if (shouldAnalyzeExecution) {
      val execution = job.analyze()
      execution.requiredFilePaths.forEach {
        println("Execution Requires File: ${it.pathString}")
      }

      execution.requiredDirectoryPaths.forEach {
        println("Execution Requires Directory: ${it.pathString}")
      }

      execution.requiredEntityPaths.forEach {
        println("Execution Requires Path: ${it.pathString}")
      }

      execution.requiredCommandNames.forEach {
        println("Execution Requires Command: $it")
      }

      execution.requiredSubCommandPatterns.forEach {
        println("Execution Requires Subcommand: ${it.joinToString(" ")}")
      }
    }

    executionEngine.execute(job)
  }

  enum class CommandRunState {
    Automatic,
    Executed,
    DryRun
  }
}
