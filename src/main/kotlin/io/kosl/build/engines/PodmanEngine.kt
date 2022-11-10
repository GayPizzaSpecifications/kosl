package io.kosl.build.engines

import io.kosl.build.BuildEngine
import io.kosl.build.BuildEngineJob
import io.kosl.context.KoslContext
import io.kosl.execution.parameter.*

class PodmanEngine: BuildEngine {
  override fun process(context: KoslContext, job: BuildEngineJob) {
    val buildCommand = mutableListOf(
      CommandName("podman"),
      SubCommandName("build"),
      RawArgument("-t"),
      RawArgument("${job.targetImageName}:${job.targetImageTag}"),
      RawArgument("-f"),
      RelativeFilePath(job.buildFilePath),
      RelativeDirectoryPath(job.contextDirectoryPath)
    )

    context.executeInteractiveProcess(buildCommand)

    if (job.push) {
      val pushCommand = mutableListOf(
        CommandName("podman"),
        SubCommandName("push"),
        RawArgument("${job.targetImageName}:${job.targetImageTag}")
      )

      context.executeInteractiveProcess(pushCommand)
    }
  }
}
