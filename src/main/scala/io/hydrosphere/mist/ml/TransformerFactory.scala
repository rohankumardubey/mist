package io.hydrosphere.mist.ml

import org.apache.spark.ml.Transformer

import scala.reflect.runtime.universe

object TransformerFactory {

  import ModelConversions._

  def apply(metadata: Metadata, data: Map[String, Any]): Transformer = {
    val runtimeMirror = universe.runtimeMirror(getClass.getClassLoader)
    val module = runtimeMirror.staticModule(metadata.className + "$")
    val obj = runtimeMirror.reflectModule(module)
    val localModel = obj.instance
    localModel.load(metadata, data)
  }
  
}
