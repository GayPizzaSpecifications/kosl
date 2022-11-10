package io.kosl.spec

import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer

@Serializable
class ServiceSpec(
  val name: String,
  val build: BuildSpec,
  val deployment: DeploymentSpec,
  val disabled: Boolean = false
) {
  companion object : KoslSpec<ServiceSpec>(serializer())
}
