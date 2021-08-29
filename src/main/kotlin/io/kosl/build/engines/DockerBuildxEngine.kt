package io.kosl.build.engines

import io.kosl.build.BuildEngine
import io.kosl.build.BuildEngineJob
import io.kosl.context.KoslContext
import io.kosl.execution.parameter.CommandName
import io.kosl.execution.parameter.RawArgument
import io.kosl.execution.parameter.RelativePath
import io.kosl.execution.parameter.SubCommandName

class DockerBuildxEngine: BuildEngine {
  override fun process(context: KoslContext, job: BuildEngineJob) {
    val command = mutableListOf(
      CommandName("docker"),
      SubCommandName("buildx"),
      SubCommandName("build"),
      RawArgument("-t"),
      RawArgument("${job.targetImageName}:${job.targetImageTag}"),
      RawArgument("-f"),
      RelativePath(job.buildFilePath)
    )

    if (job.push) {
      command.add(RawArgument("--push"))
    } else {
      command.add(RawArgument("--load"))
    }

    command.add(RelativePath(job.contextDirectoryPath))
    context.executeInteractiveProcess(command)
  }
}
