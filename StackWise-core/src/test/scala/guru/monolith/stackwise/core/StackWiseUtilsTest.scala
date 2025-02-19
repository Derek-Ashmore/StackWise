/*******************************************************************************
 * Copyright (c) 2016 Break The Monolith, Derek C. Ashmore and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Contributors:
 *    Derek C. Ashmore - initial API and implementation
 *******************************************************************************/
package guru.monolith.stackwise.core

import org.junit.Test
import org.junit.Assert

@Test
class StackWiseUtilsTest extends StackWiseTestBase {
  
  @Test
  def testFindThreads {
    val blockedThreads = StackWiseUtils.findThreads(stack4, Array(Thread.State.BLOCKED))
    Assert.assertEquals(6, blockedThreads.length)
    blockedThreads.foreach { stack => Assert.assertEquals(Thread.State.BLOCKED, stack.state) }
  }
  
  @Test
  def testmapThreadsById {
    val idThreadMap = StackWiseUtils.mapThreadsById(stack1)
    Assert.assertEquals(66, idThreadMap.size)
    Assert.assertTrue(idThreadMap.contains("t@172"))
  }
  
  @Test
  def testfindLockOwnership {
    val lockOwnershipMap = StackWiseUtils.findLockOwnership(stack4)
    Assert.assertEquals("0x000000001aa19800", lockOwnershipMap.get("0x0000000682cb47d8").get)
  }
  
  @Test
  def testfindDesiredLock {
    val lockResource = StackWiseUtils.findDesiredLock(stack4.head)
    Assert.assertEquals("0x000000068c17f6a8", lockResource.monitorLockName)
    Assert.assertEquals("java.lang.Object", lockResource.lockedClassName)
  }
  
  @Test
  def testfindBlockingThreads {
    val blockingThreadList = StackWiseUtils.findBlockingThreads(stack4)
    Assert.assertEquals(3, blockingThreadList.size)
  }
  
   @Test
  def testfindBlockerUnknownThreads {
    val strandedBlockersList = StackWiseUtils.findBlockerUnknownThreads(stack2)
    Assert.assertEquals(23, strandedBlockersList.size)
    
    val strandedBlockersList4 = StackWiseUtils.findBlockerUnknownThreads(stack4)
    Assert.assertEquals(0, strandedBlockersList4.size)
  }
   
   @Test
   def testfindBlockingResources {
     val blockingResourceList = StackWiseUtils.findBlockingResources(stackDeadlock)
     Assert.assertEquals(2, blockingResourceList.size)
   }
   
   @Test
   def testfindThreadsReferencingSpecificClasses {
     val blockingResourceList = StackWiseUtils.findThreadsReferencingSpecificClasses(stack6, Array("java.net", "java.io", "java.nio"))
     Assert.assertEquals(10001, blockingResourceList.size)
   }
   
}