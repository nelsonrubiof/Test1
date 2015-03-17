package com.scopix.util
{
import flash.utils.ByteArray;
	import flash.utils.getDefinitionByName;
	import flash.utils.getQualifiedClassName;
 
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.utils.ObjectUtil;
 
	/** 
	 * Collection of static function I've used to clone objects and data; each method 
	 * has certain benefits and liabilities which are outlined below.
	 * 
	 * */
	public class Cloner
	{
		/**
		 * Create a clone of the provided object, maintaining class and dependencies.
		 * Clone is Shallow, meaning that complex property (objects/arrays) will 
		 * translate as pointers, not duplicates.
		 * 
		 * This method is best suited for cloning simple objects, such as data objects
		 * and models.
		 * 
		 * Benefits: Maintains any inheritance in the cloned object and also maintains 
		 * class/superclass methods since a genuine new instance of the class is being 
		 * created, as opposed to some other cloning methods which copy data only (see byteCopy).
		 * 
		 * Limitations: cloned class must not require initter arguments, for example
		 * new Class(foo) won't work.
		 * 
		 * */
		public static function simpleClone(source:*):*
	    {
	    	var ClassName:String = getQualifiedClassName(source);
	    	var clone:* = getInstanceByName(ClassName);
			clone = shallowCopy(source, clone);
		    return clone;
	    }
 
	    /** 
	    * Similar to simpleClone(), except that the clone will attempt to coerce itself into 
	    * being of classtype destinationRef. Subject to the same limitations and benefits 
	    * of simpleClone(), except as noted below.
	    * 
	    * destinationRef specifies what Class you want the clone to be, and may be any of the following:
	    * 	* 	A string referring to a valid Class name, eg "nichpro.managers.Worklist:WorklistModel".
	    * 	* 	An actual Class, eg nichpro.managers.Worklist.WorklistModel.
	    *	*	An instance of the the desired destination class eg  foo:WorklistModel
	    *	*	Null in which case shallowCast() will simply duplicate the effects of simpleClone();
	    * 
	    * useDestinationRefObject is only applicable when destinationRef is an instance of a class.
	    * When true, shallowCast will attempt to merge source into the destinationRef object, returning
	    * destinationRef, rather than a clone. When false, shallowCast will merely use the destinationRef
	    * as a reference for the type of object it needs to create, and will otherwise leave the
	    * destinationRef object untouched.
	    * 
	    * This method works best when destinationRef is a dynamic class, or when you know
	    * for sure that the data fields you need to clone over are 100% translatable.
	    * 
	    * source can be null, in which case the class instance created by destinationRef is returned. If
	    * both values are null, we'll probably crash.
	    * 
	    * Use at your own risk.
	    * 
	    * */
		public static function shallowCast(source:*, destinationRef:*=null, useDestinationRefObject:Boolean=false):*
	    {
	    	var clone:*;
	    	var ClassName:String
	    	if( (source!=null) && (destinationRef == null) ){
	    		ClassName = getQualifiedClassName(source);
	    	} else if(destinationRef is String) {
	    		ClassName = destinationRef;
	    	} else {
	    		ClassName = getQualifiedClassName(destinationRef);
	    	}
 
			clone = getInstanceByName(ClassName);
			clone = shallowCopy(source, clone);
		    return clone;
	    }
 
	    public static function castCollection(collection:ArrayCollection, castAs:*):ArrayCollection {
	    	var results:ArrayCollection = new ArrayCollection();
	    	for each (var obj:* in collection) {
	    		var casted:* = shallowCast(obj, castAs, false);
	    		results.addItem(casted);
	    	}
	    	return results;
	    }
	    /**
	    * Copy props from source to dest. Assumes dest is either dynamic or that its 
	    * property names match those in source, otherwise an error occurs.
	    * 
	    * Returns the modified dest, although in general this will not be needed as
	    * dest is assumed to be an object reference.
	    * */
	    public static function shallowCopy(source:*, dest:*):* {
	    	var classInfo:Object = ObjectUtil.getClassInfo(source, ["mx_internal_uid"])
	    	if(source!=null && dest !=null){
				for each(var prop:* in classInfo.properties){
					var propName:String = String(prop); // prop is actually a QName at this stage.
					if(dest.hasOwnProperty(propName)){
						dest[propName]=source[propName];
					} else {
						trace("Cloner: Property '" + propName + "' not found on destination Object!")
					}
				}
	    	}
		    return dest;
 
	    }
 
	    /**
	    * Given a valid classname as a string, return a new instance of that class.
	    * 
	    * */
	    public static function getInstanceByName(ClassName:String):* {
	    	var ClassReference:Class = getDefinitionByName(ClassName) as Class;
		    return new ClassReference();
	    }
 
 
		/**
		 * Creates a deep clone of the source object. Subarrays and subobjects will also
		 * be cloned, making this method ideal for cloning complex Arrays and other pure
		 * data models. 
		 * 
		 * Limitations: cloned object will be returned as an Object, not as the original Class; 
		 * class specific methods and inheritances are therefore lost in translation if used 
		 * to copy complex objects.
		 * 
		 * */
		public static function byteCopy(source:Object):*
		{
		    var myBA:ByteArray = new ByteArray();
		    myBA.writeObject(source);
		    myBA.position = 0;
		    var clone:* = myBA.readObject();
		    return clone;
		}
 
 
		/** 
		 * Given a Class name (as a string) and a property list, 
		 * recreate an object. This will be a deep copy; i.e. there will
		 * be no ties whatsoever to the original object. Returns null if 
		 * the deep copy fails for any reason.
		 * 
		 * props can either be an XMLList of properties and names, or the object
		 * itself which will be broken down and copied.
		 * 
		 * By design, all Objects displayable within a ProtocolGrid must be
		 * flat, ie all properties must be primitives; no objects or arrays
		 * are allowed. If at some point this becomes a reqquirement we
		 * should invesstigate using a json parser or flash.utils.describeType
		 * instead to represent and replicate objects.
		 * 
		 * Note that I'm not explicitly setting variable types; rather I am relying upon
		 * the class definitions and Flex's own parser to determine that an xml value
		 * of "true" sent to a boolean should be parsed as a boolean. So far, so good,
		 * but if weirdness develops this is a potential weakness that may need to 
		 * be tied down.
		 * 
		 * */
		 public function classNameToClassInstance(className:String):* {
		 	var success:Boolean = true;
		 	var result:*;
		 	var ClassReference:Class;
			try{
				ClassReference = getDefinitionByName(className) as Class;				
			} catch(e:ReferenceError){				
				success=false;
				return null;
			}
			if(success){
				result = new ClassReference();
			}
			return result;
		}
 
 
		public static function recreateClassObject(ClassName:String, props:*):* {
 
			//trace(describeType(props));
			var pendingData:*;
			var success:Boolean = true;
			var ClassReference:Class;
			var prop:*;
			var structMismatch:Boolean = false;
 
			if(ClassName!=""){
 
				// First, does the class even exist?
 				try{
 					ClassReference = getDefinitionByName(ClassName) as Class;
 				} catch(e:ReferenceError){
 					success=false;
					Alert.show("Unable to populate template: unknown type:" + ClassName,"Failed Load")
				}
 
				if(success){
					// If it exists, are we able to instanciate it without providing any 
					// arguments?
					try{
						pendingData = new ClassReference();
					} catch(e:Error) {
						success=false;
						Alert.show("Unable to populate template, class '"+ ClassName + "' failed to initialize.","Failed Load")
					}
 
					if(success){
						// Class exists and has been instanciated; populate it.
						if(props is XMLList){
							// if props is XML, it means we are importing it from the database
							// as a string to reconstruct. Read the XML and rebuild the object
							// based on what we think we know:
							for each (prop in props){
								if(pendingData.hasOwnProperty(prop.@name)){
									pendingData[prop.@name] = prop;
								} else {
									structMismatch=true;
								}
							}
						} else {
							// If, however, its *not* XML, then we assume we have been passed the 
							// actual object as an argument. Analyze the object and pass copies 
							// of the props into our clone:
							var classInfo:Object = ObjectUtil.getClassInfo(props)
							for each(prop in classInfo.properties){
								if(pendingData.hasOwnProperty(prop)){
									pendingData[prop] = props[prop];
								} else if (props[prop]!=null) {
									structMismatch=true;
									// trace("recreateClassObject: Unknown Non-Null Property:" +  prop + "  value:" + props[prop])
								}								
							}
						}
						if(structMismatch){
							trace("WARNING: Data mismatch in " + props + " not all properties could be written to " + pendingData);
						}
						return pendingData;		
					}
					return null		
				}
				return null		
			}	
			return null		
		}
	}
}