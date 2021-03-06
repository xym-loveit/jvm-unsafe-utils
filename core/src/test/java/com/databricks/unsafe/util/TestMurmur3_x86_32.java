/*
 * Copyright 2014 Databricks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.databricks.unsafe.util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Test file based on Guava's Murmur3Hash32Test.
 */
public class TestMurmur3_x86_32 {

  private static Murmur3_x86_32 hasher = new Murmur3_x86_32(0);

  @Test
  public void testKnownIntegerInputs() {
    Assert.assertEquals(593689054, hasher.hashInt(0));
    Assert.assertEquals(-189366624, hasher.hashInt(-42));
    Assert.assertEquals(-1134849565, hasher.hashInt(42));
    Assert.assertEquals(-1718298732, hasher.hashInt(Integer.MIN_VALUE));
    Assert.assertEquals(-1653689534, hasher.hashInt(Integer.MAX_VALUE));
  }

  @Test
  public void testKnownLongInputs() {
    Assert.assertEquals(1669671676, hasher.hashLong(0L));
    Assert.assertEquals(-846261623, hasher.hashLong(-42L));
    Assert.assertEquals(1871679806, hasher.hashLong(42L));
    Assert.assertEquals(1366273829, hasher.hashLong(Long.MIN_VALUE));
    Assert.assertEquals(-2106506049, hasher.hashLong(Long.MAX_VALUE));
  }

  @Test
  public void randomizedStressTest() {
    int size = 65536;
    Random rand = new Random();

    // A set used to track collision rate.
    Set<Integer> hashcodes = new HashSet<Integer>();
    for (int i = 0; i < size; i++) {
      int vint = rand.nextInt();
      long lint = rand.nextLong();
      Assert.assertEquals(hasher.hashInt(vint), hasher.hashInt(vint));
      Assert.assertEquals(hasher.hashLong(lint), hasher.hashLong(lint));

      hashcodes.add(hasher.hashLong(lint));
    }

    // A very loose bound.
    Assert.assertTrue(hashcodes.size() > size * 0.95);
  }
}
