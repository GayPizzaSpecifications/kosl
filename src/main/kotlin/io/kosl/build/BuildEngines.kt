package io.kosl.build

enum class BuildEngines(val id: String, val engine: BuildEngine) {
  DockerBuild("docker-build", DockerBuildEngine()),
  BuildKit("buildkit", BuildKitEngine()),
  Buildah("buildah", BuildahEngine())
}
