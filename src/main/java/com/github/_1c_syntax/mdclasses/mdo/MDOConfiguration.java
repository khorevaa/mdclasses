/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2020
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 *
 * MDClasses is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * MDClasses is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with MDClasses.
 */
package com.github._1c_syntax.mdclasses.mdo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import com.github._1c_syntax.mdclasses.metadata.additional.UseMode;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import java.util.Map;

import static com.github._1c_syntax.mdclasses.utils.MapExtension.getOrEmptyString;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@JsonRootName(value = "Configuration")
@JsonDeserialize(builder = MDOConfiguration.MDOConfigurationBuilderImpl.class)
@SuperBuilder
public class MDOConfiguration extends MDObjectBase {

  @Builder.Default
  protected ScriptVariant scriptVariant = ScriptVariant.ENGLISH;
  protected CompatibilityMode compatibilityMode;
  protected CompatibilityMode configurationExtensionCompatibilityMode;
  @Builder.Default
  protected UseMode modalityUseMode = UseMode.USE;
  @Builder.Default
  protected UseMode synchronousExtensionAndAddInCallUseMode = UseMode.USE;
  @Builder.Default
  protected UseMode synchronousPlatformExtensionAndAddInCallUseMode = UseMode.USE;

  protected String defaultRunMode;
  protected String defaultLanguage;
  protected String dataLockControlMode;
  protected String objectAutonumerationMode;

  @Override
  public MDOType getType() {
    return MDOType.CONFIGURATION;
  }

  @JsonPOJOBuilder(withPrefix = "")
  @JsonIgnoreProperties(ignoreUnknown = true)
  static final class MDOConfigurationBuilderImpl extends
    MDOConfiguration.MDOConfigurationBuilder<MDOConfiguration, MDOConfiguration.MDOConfigurationBuilderImpl> {

    @JsonProperty("Properties")
    @Override
    public MDOConfigurationBuilderImpl properties(Map<String, Object> properties) {
      super.properties(properties);

      scriptVariant(ScriptVariant.fromValue((String) properties.getOrDefault("ScriptVariant",
        ScriptVariant.ENGLISH.value())));
      compatibilityMode(new CompatibilityMode(getOrEmptyString(properties, "CompatibilityMode")));
      configurationExtensionCompatibilityMode(new CompatibilityMode(getOrEmptyString(properties,
        "ConfigurationExtensionCompatibilityMode")));
      defaultRunMode(getOrEmptyString(properties, "DefaultRunMode"));
      defaultLanguage(getOrEmptyString(properties, "DefaultLanguage"));
      dataLockControlMode(getOrEmptyString(properties, "DataLockControlMode"));
      objectAutonumerationMode(getOrEmptyString(properties, "ObjectAutonumerationMode"));
      modalityUseMode(UseMode.fromValue((String) properties.getOrDefault("ModalityUseMode",
        UseMode.USE.value())));
      synchronousExtensionAndAddInCallUseMode(UseMode.fromValue(
        (String) properties.getOrDefault("SynchronousExtensionAndAddInCallUseMode",
          UseMode.USE.value())));
      synchronousPlatformExtensionAndAddInCallUseMode(UseMode.fromValue(
        (String) properties.getOrDefault("SynchronousPlatformExtensionAndAddInCallUseMode",
          UseMode.USE.value())));

      return this.self();
    }
  }

}
