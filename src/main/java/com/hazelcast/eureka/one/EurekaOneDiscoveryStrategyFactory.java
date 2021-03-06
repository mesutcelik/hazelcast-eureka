/*
 * Copyright (c) 2008-2017, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.eureka.one;

import com.google.common.collect.Lists;
import com.hazelcast.config.properties.PropertyDefinition;
import com.hazelcast.eureka.one.EurekaOneDiscoveryStrategy.EurekaOneDiscoveryStrategyBuilder;
import com.hazelcast.logging.ILogger;
import com.hazelcast.spi.discovery.DiscoveryNode;
import com.hazelcast.spi.discovery.DiscoveryStrategy;
import com.hazelcast.spi.discovery.DiscoveryStrategyFactory;
import com.netflix.discovery.EurekaClient;

import java.util.Collection;
import java.util.Map;

/**
 * <p>Configuration class of the Hazelcast Discovery Plugin for Eureka.</p>
 * <p>For possible configuration properties please refer to the public constants of this class.</p>
 */
public class EurekaOneDiscoveryStrategyFactory
        implements DiscoveryStrategyFactory {

    private static final Collection<PropertyDefinition> PROPERTY_DEFINITIONS = Lists.newArrayList(
            EurekaOneProperties.SELF_REGISTRATION,
            EurekaOneProperties.NAMESPACE);

    private static EurekaClient eurekaClient;

    public Class<? extends DiscoveryStrategy> getDiscoveryStrategyType() {
        return EurekaOneDiscoveryStrategy.class;
    }

    public DiscoveryStrategy newDiscoveryStrategy(DiscoveryNode discoveryNode, ILogger logger,
                                                  Map<String, Comparable> properties) {
        EurekaOneDiscoveryStrategyBuilder builder = new EurekaOneDiscoveryStrategyBuilder();
        builder.setDiscoveryNode(discoveryNode).setILogger(logger).setProperties(properties)
                .setEurekaClient(eurekaClient);
        return builder.build();
    }

    public Collection<PropertyDefinition> getConfigurationProperties() {
        return PROPERTY_DEFINITIONS;
    }

    /**
     * Allows to use already configured {@link EurekaClient} instead of creating new one.
     *
     * @param eurekaClient {@link EurekaClient} instance
     */
    public static void setEurekaClient(EurekaClient eurekaClient) {
        EurekaOneDiscoveryStrategyFactory.eurekaClient = eurekaClient;
    }
}
