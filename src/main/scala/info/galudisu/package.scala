package info

import org.jooq.conf._

package object galudisu {

  type Every30Seconds = String
  val Every30Seconds = "Every30Seconds"

  type Every5Seconds = String
  val Every5Seconds = "Every5Seconds"

  type FlowFlagExp = String
  val FlowFlagExp = "FlowFlagExp"

  val jooqSettings: Settings = {
    System.getProperties.setProperty("org.jooq.no-logo", "true")
    SettingsTools
      .defaultSettings()
      .withRenderKeywordCase(RenderKeywordCase.UPPER)
      .withRenderKeywordStyle(RenderKeywordStyle.UPPER)
      .withRenderFormatted(true)
  }
}
