/*
 * Copyright (C) 2012  Krawler Information Systems Pvt Ltd
 * All rights reserved.
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
(function(){var EV=Wtf.lib.Event;Wtf.ux.ManagedIFrame=function(){var args=Array.prototype.slice.call(arguments,0),el=Wtf.get(args[0]),config=args[0];if(el&&el.dom&&el.dom.tagName=="IFRAME"){config=args[1]||{}}else{config=args[0]||args[1]||{};el=config.autoCreate?Wtf.get(Wtf.DomHelper.append(config.autoCreate.parent||document.body,Wtf.apply({tag:"iframe",src:(Wtf.isIE&&Wtf.isSecure)?Wtf.SSL_SECURE_URL:""},config.autoCreate))):null}if(!el||el.dom.tagName!="IFRAME"){return el}el.dom.name||(el.dom.name=el.dom.id);this.addEvents({"focus":true,"blur":true,"unload":true,"domready":true,"documentloaded":true,"exception":true,"message":true});if(config.listeners){this.listeners=config.listeners;Wtf.ux.ManagedIFrame.superclass.constructor.call(this)}Wtf.apply(el,this);el.addClass("x-managed-iframe");if(config.style){el.applyStyles(config.style)}el._maskEl=el.parent(".x-managed-iframe-mask")||el.parent().addClass("x-managed-iframe-mask");Wtf.apply(el,{disableMessaging:config.disableMessaging===true,loadMask:Wtf.apply({msg:"Loading..",msgCls:"x-mask-loading",maskEl:el._maskEl,hideOnReady:true,disabled:!config.loadMask},config.loadMask),_eventName:Wtf.isIE?"onreadystatechange":"onload",_windowContext:null,eventsFollowFrameLinks:typeof config.eventsFollowFrameLinks=="undefined"?true:config.eventsFollowFrameLinks});el.dom[el._eventName]=el.loadHandler.createDelegate(el);if(document.addEventListener){Wtf.EventManager.on(window,"DOMFrameContentLoaded",el.dom[el._eventName])}var um=el.updateManager=new Wtf.UpdateManager(el,true);um.showLoadIndicator=config.showLoadIndicator||false;if(config.src){el.setSrc(config.src)}else{var content=config.html||config.content||false;if(content){el.update.defer(10,el,[content])}}return Wtf.ux.ManagedIFrame.Manager.register(el)};var MIM=Wtf.ux.ManagedIFrame.Manager=function(){var frames={};return{shimCls:"x-frame-shim",register:function(frame){frame.manager=this;frames[frame.id]=frames[frame.dom.name]={ref:frame,elCache:{}};return frame},deRegister:function(frame){frame._unHook();delete frames[frame.id];delete frames[frame.dom.name]},hideShims:function(){if(!this.shimApplied){return }Wtf.select("."+this.shimCls,true).removeClass(this.shimCls+"-on");this.shimApplied=false},showShims:function(){if(!this.shimApplied){this.shimApplied=true;Wtf.select("."+this.shimCls,true).addClass(this.shimCls+"-on")}},getFrameById:function(id){return typeof id=="string"?(frames[id]?frames[id].ref||null:null):null},getFrameByName:function(name){return this.getFrameById(name)},getFrameHash:function(frame){return frame.id?frames[frame.id]:null},eventProxy:function(e){e=Wtf.lib.Event.getEvent(e);if(!e){return }var be=e.browserEvent||e;if(e.type=="unload"){this._unHook()}if(!be["eventPhase"]||(be["eventPhase"]==(be["AT_TARGET"]||2))){return this.fireEvent(e.type,e)}},_flyweights:{},removeNode:Wtf.isIE?function(frame,n){frame=MIM.getFrameHash(frame);if(frame&&n&&n.tagName!="BODY"){d=frame.scratchDiv||(frame.scratchDiv=frame.getDocument().createElement("div"));d.appendChild(n);d.innerHTML=""}}:function(frame,n){if(n&&n.parentNode&&n.tagName!="BODY"){n.parentNode.removeChild(n)}}}}();MIM.showDragMask=MIM.showShims;MIM.hideDragMask=MIM.hideShims;MIM.El=function(frame,el,forceNew){var frameObj;frame=(frameObj=MIM.getFrameHash(frame))?frameObj.ref:null;if(!frame){return null}var elCache=frameObj.elCache||(frameObj.elCache={});var dom=frame.getDom(el);if(!dom){return null}var id=dom.id;if(forceNew!==true&&id&&elCache[id]){return elCache[id]}this.dom=dom;this.id=id||Wtf.id(dom)};MIM.El.get=function(frame,el){var ex,elm,id,doc;if(!frame||!el){return null}var frameObj;frame=(frameObj=MIM.getFrameHash(frame))?frameObj.ref:null;if(!frame){return null}var elCache=frameObj.elCache||(frameObj.elCache={});if(!(doc=frame.getDocument())){return null}if(typeof el=="string"){if(!(elm=frame.getDom(el))){return null}if(ex=elCache[el]){ex.dom=elm}else{ex=elCache[el]=new MIM.El(frame,elm)}return ex}else{if(el.tagName){if(!(id=el.id)){id=Wtf.id(el)}if(ex=elCache[id]){ex.dom=el}else{ex=elCache[id]=new MIM.El(frame,el)}return ex}else{if(el instanceof MIM.El){if(el!=frameObj.docEl){el.dom=frame.getDom(el.id)||el.dom;elCache[el.id]=el}return el}else{if(el.isComposite){return el}else{if(Wtf.isArray(el)){return frame.select(el)}else{if(el==doc){if(!frameObj.docEl){var f=function(){};f.prototype=MIM.El.prototype;frameObj.docEl=new f();frameObj.docEl.dom=doc}return frameObj.docEl}}}}}}return null};Wtf.apply(MIM.El.prototype,Wtf.Element.prototype);Wtf.extend(Wtf.ux.ManagedIFrame,Wtf.util.Observable,{src:null,setSrc:function(url,discardUrl,callback){var reset=Wtf.isIE&&Wtf.isSecure?Wtf.SSL_SECURE_URL:"";var src=url||this.src||reset;if(Wtf.isOpera){this.dom.src=reset}this._windowContext=null;this._unHook();this._callBack=callback||false;this.showMask();(function(){var s=typeof src=="function"?src()||"":src;try{this._frameAction=true;this.dom.src=s;this.frameInit=true;this.checkDOM()}catch(ex){this.fireEvent("exception",this,ex)}}).defer(10,this);if(discardUrl!==true){this.src=src}return this},reset:function(src,callback){this.setSrc(src||(Wtf.isIE&&Wtf.isSecure?Wtf.SSL_SECURE_URL:""),true,callback)},scriptRE:/(?:<script.*?>)((\n|\r|.)*?)(?:<\/script>)/gi,update:function(content,loadScripts,callback){loadScripts=loadScripts||this.getUpdateManager().loadScripts||false;content=Wtf.DomHelper.markup(content||"");content=loadScripts===true?content:content.replace(this.scriptRE,"");var doc;if(doc=this.getDocument()){this._frameAction=!!content.length;this._windowContext=this.src=null;this._callBack=callback||false;this._unHook();this.showMask();doc.open();doc.write(content);doc.close();this.frameInit=true;if(this._frameAction){this.checkDOM()}else{this.hideMask(true);if(this._callBack){this._callBack()}}}else{this.hideMask(true);if(this._callBack){this._callBack()}}return this},disableMessaging:true,_XFrameMessaging:function(){var tagStack={"$":[]};var isEmpty=function(v,allowBlank){return v===null||v===undefined||(!allowBlank?v==="":false)};window.sendMessage=function(message,tag,origin){var MIF;if(MIF=arguments.callee.manager){if(message._fromHost){var fn,result;var compTag=message.tag||tag||null;var mstack=!isEmpty(compTag)?tagStack[compTag.toLowerCase()]||[]:tagStack["$"];for(var i=0,l=mstack.length;i<l;i++){if(fn=mstack[i]){result=fn.apply(fn.__scope,arguments)===false?false:result;if(fn.__single){mstack[i]=null}if(result===false){break}}}return result}else{message={type:isEmpty(tag)?"message":"message:"+tag.toLowerCase().replace(/^\s+|\s+$/g,""),data:message,domain:origin||document.domain,uri:document.documentURI,source:window,tag:isEmpty(tag)?null:tag.toLowerCase()};try{return MIF.disableMessaging!==true?MIF.fireEvent.call(MIF,message.type,MIF,message):null}catch(ex){}return null}}};window.onhostmessage=function(fn,scope,single,tag){if(typeof fn=="function"){if(!isEmpty(fn.__index)){throw"onhostmessage: duplicate handler definition"+(tag?" for tag:"+tag:"")}var k=isEmpty(tag)?"$":tag.toLowerCase();tagStack[k]||(tagStack[k]=[]);Wtf.apply(fn,{__tag:k,__single:single||false,__scope:scope||window,__index:tagStack[k].length});tagStack[k].push(fn)}else{throw"onhostmessage: function required"}};window.unhostmessage=function(fn){if(typeof fn=="function"&&typeof fn.__index!="undefined"){var k=fn.__tag||"$";tagStack[k][fn.__index]=null}}},get:function(el){return MIM.El.get(this,el)},fly:function(el,named){named=named||"_global";el=this.getDom(el);if(!el){return null}if(!MIM._flyweights[named]){MIM._flyweights[named]=new Wtf.Element.Flyweight()}MIM._flyweights[named].dom=el;return MIM._flyweights[named]},getDom:function(el){var d;if(!el||!(d=this.getDocument())){return null}return el.dom?el.dom:(typeof el=="string"?d.getElementById(el):el)},select:function(selector,unique){var d;return(d=this.getDocument())?Wtf.Element.select(selector,unique,d):null},query:function(selector){var d;return(d=this.getDocument())?Wtf.DomQuery.select(selector,d):null},getDoc:function(){return this.get(this.getDocument())},removeNode:function(node){MIM.removeNode(this,this.getDom(node))},_unHook:function(){var elcache,h=MIM.getFrameHash(this)||{};if(this._hooked&&h&&(elcache=h.elCache)){for(var id in elcache){var el=elcache[id];delete elcache[id];if(el.removeAllListeners){el.removeAllListeners()}}if(h.docEl){h.docEl.removeAllListeners();h.docEl=null;delete h.docEl}}this._hooked=this._domReady=this._domFired=false},_renderHook:function(){this._windowContext=null;this._hooked=false;try{if(this.writeScript('(function(){(window.hostMIF = parent.Wtf.get("'+this.dom.id+'"))._windowContext='+(Wtf.isIE?"window":"{eval:function(s){return eval(s);}}")+";})();")){this._frameProxy||(this._frameProxy=MIM.eventProxy.createDelegate(this));EV.doAdd(this.getWindow(),"focus",this._frameProxy);EV.doAdd(this.getWindow(),"blur",this._frameProxy);EV.doAdd(this.getWindow(),"unload",this._frameProxy);if(this.disableMessaging!==true){this.loadFunction({name:"XMessage",fn:this._XFrameMessaging},false,true);var sm;if(sm=this.getWindow().sendMessage){sm.manager=this}}this.CSS=new CSSInterface(this.getDocument())}return this.domWritable()}catch(ex){}return false},sendMessage:function(message,tag,origin){var win;if(this.disableMessaging!==true&&(win=this.getWindow())){tag||(tag=message.tag||"");tag=tag.toLowerCase();message=Wtf.applyIf(message.data?message:{data:message},{type:Wtf.isEmpty(tag)?"message":"message:"+tag,domain:origin||document.domain,uri:document.documentURI,source:window,tag:tag||null,_fromHost:this});return win.sendMessage?win.sendMessage.call(null,message,tag,origin):null}return null},_windowContext:null,getDocument:function(){var win;return(win=this.getWindow())?win.document:null},getBody:function(){var d;return(d=this.getDocument())?d.body:null},getDocumentURI:function(){var URI;try{URI=this.src?this.getDocument().location.href:null}catch(ex){}return URI||this.src},getWindow:function(){var dom=this.dom;return dom?dom.contentWindow||window.frames[dom.name]:null},print:function(){try{var win=this.getWindow();if(Wtf.isIE){win.focus()}win.print()}catch(ex){throw"print exception: "+(ex.description||ex.message||ex)}},destroy:function(){this.removeAllListeners();if(this.dom){if(document.addEventListener){Wtf.EventManager.un(window,"DOMFrameContentLoaded",this.dom[this._eventName])}this.dom[this._eventName]=null;Wtf.ux.ManagedIFrame.Manager.deRegister(this);this._windowContext=null;if(Wtf.isIE&&this.dom.src){this.dom.src="javascript:false"}this._maskEl=null;Wtf.removeNode(this.dom)}Wtf.apply(this.loadMask,{masker:null,maskEl:null})},domWritable:function(){return !!this._windowContext},execScript:function(block,useDOM){try{if(this.domWritable()){if(useDOM){this.writeScript(block)}else{return this._windowContext.eval(block)}}else{throw"execScript:non-secure context"}}catch(ex){this.fireEvent("exception",this,ex);return false}return true},writeScript:function(block,attributes){attributes=Wtf.apply({},attributes||{},{type:"text/javascript",text:block});try{var head,script,doc=this.getDocument();if(doc&&doc.getElementsByTagName){if(!(head=doc.getElementsByTagName("head")[0])){head=doc.createElement("head");doc.getElementsByTagName("html")[0].appendChild(head)}if(head&&(script=doc.createElement("script"))){for(var attrib in attributes){if(attributes.hasOwnProperty(attrib)&&attrib in script){script[attrib]=attributes[attrib]}}return !!head.appendChild(script)}}}catch(ex){this.fireEvent("exception",this,ex)}return false},loadFunction:function(fn,useDOM,invokeIt){var name=fn.name||fn;var fn=fn.fn||window[fn];this.execScript(name+"="+fn,useDOM);if(invokeIt){this.execScript(name+"()")}},showMask:function(msg,msgCls,forced){var lmask;if((lmask=this.loadMask)&&(!lmask.disabled||forced)){if(lmask._vis){return }lmask.masker||(lmask.masker=Wtf.get(lmask.maskEl||this.dom.parentNode||this.wrap({tag:"div",style:{position:"relative"}})));lmask._vis=true;lmask.masker.mask.defer(lmask.delay||5,lmask.masker,[msg||lmask.msg,msgCls||lmask.msgCls])}},hideMask:function(forced){var tlm;if((tlm=this.loadMask)&&!tlm.disabled&&tlm.masker){if(!forced&&(tlm.hideOnReady!==true&&this._domReady)){return }tlm._vis=false;tlm.masker.unmask.defer(tlm.delay||5,tlm.masker)}},loadHandler:function(e){if(!this.frameInit||(!this._frameAction&&!this.eventsFollowFrameLinks)){return }var rstatus=(e&&typeof e.type!=="undefined"?e.type:this.dom.readyState);switch(rstatus){case"loading":case"interactive":break;case"DOMFrameContentLoaded":if(this._domFired||(e&&e.target!==this.dom)){return }case"domready":if(this._domFired){return }if(this._domFired=this._hooked=this._renderHook()){this._frameAction=(this.fireEvent("domready",this)===false?false:this._frameAction)}case"domfail":this._domReady=true;this.hideMask();break;case"load":case"complete":if(!this._domFired){this.loadHandler({type:"domready"})}this.hideMask(true);if(this._frameAction||this.eventsFollowFrameLinks){this.fireEvent.defer(50,this,["documentloaded",this])}this._frameAction=false;if(this.eventsFollowFrameLinks){this._domFired=this._domReady=false}if(this._callBack){this._callBack(this)}break;default:}},checkDOM:function(win){if(Wtf.isOpera){return }var n=0,win=win||this.getWindow(),manager=this,domReady=false,max=100;var poll=function(){try{domReady=false;var doc=win.document,body;if(!manager._domReady){domReady=(doc&&doc.getElementsByTagName);domReady=domReady&&(body=doc.getElementsByTagName("body")[0])&&!!body.innerHTML.length}}catch(ex){n=max}if(!manager._frameAction||manager._domReady){return }if(n++<max&&!domReady){setTimeout(arguments.callee,10);return }manager.loadHandler({type:domReady?"domready":"domfail"})};setTimeout(poll,50)}});var styleCamelRe=/(-[a-z])/gi;var styleCamelFn=function(m,a){return a.charAt(1).toUpperCase()};var CSSInterface=function(hostDocument){var doc;if(hostDocument){doc=hostDocument;return{rules:null,createStyleSheet:function(cssText,id){var ss;if(!doc){return }var head=doc.getElementsByTagName("head")[0];var rules=doc.createElement("style");rules.setAttribute("type","text/css");if(id){rules.setAttribute("id",id)}if(Wtf.isIE){head.appendChild(rules);ss=rules.styleSheet;ss.cssText=cssText}else{try{rules.appendChild(doc.createTextNode(cssText))}catch(e){rules.cssText=cssText}head.appendChild(rules);ss=rules.styleSheet?rules.styleSheet:(rules.sheet||doc.styleSheets[doc.styleSheets.length-1])}this.cacheStyleSheet(ss);return ss},removeStyleSheet:function(id){if(!doc){return }var existing=doc.getElementById(id);if(existing){existing.parentNode.removeChild(existing)}},swapStyleSheet:function(id,url){this.removeStyleSheet(id);if(!doc){return }var ss=doc.createElement("link");ss.setAttribute("rel","stylesheet");ss.setAttribute("type","text/css");ss.setAttribute("id",id);ss.setAttribute("href",url);doc.getElementsByTagName("head")[0].appendChild(ss)},refreshCache:function(){return this.getRules(true)},cacheStyleSheet:function(ss){if(this.rules){this.rules={}}try{var ssRules=ss.cssRules||ss.rules;for(var j=ssRules.length-1;j>=0;--j){this.rules[ssRules[j].selectorText]=ssRules[j]}}catch(e){}},getRules:function(refreshCache){if(this.rules==null||refreshCache){this.rules={};if(doc){var ds=doc.styleSheets;for(var i=0,len=ds.length;i<len;i++){try{this.cacheStyleSheet(ds[i])}catch(e){}}}}return this.rules},getRule:function(selector,refreshCache){var rs=this.getRules(refreshCache);if(!Wtf.isArray(selector)){return rs[selector]}for(var i=0;i<selector.length;i++){if(rs[selector[i]]){return rs[selector[i]]}}return null},updateRule:function(selector,property,value){if(!Wtf.isArray(selector)){var rule=this.getRule(selector);if(rule){rule.style[property.replace(styleCamelRe,styleCamelFn)]=value;return true}}else{for(var i=0;i<selector.length;i++){if(this.updateRule(selector[i],property,value)){return true}}}return false}}}};Wtf.ux.ManagedIframePanel=Wtf.extend(Wtf.Panel,{defaultSrc:null,bodyStyle:{height:"100%",width:"100%",position:"relative"},frameStyle:{overflow:"auto"},frameConfig:null,hideMode:!Wtf.isIE?"nosize":"display",shimCls:Wtf.ux.ManagedIFrame.Manager.shimCls,shimUrl:null,loadMask:false,animCollapse:Wtf.isIE,autoScroll:false,closable:true,ctype:"Wtf.ux.ManagedIframePanel",showLoadIndicator:false,unsupportedText:"Inline frames are NOT enabled/supported by your browser.",initComponent:function(){var unsup=this.unsupportedText?{html:this.unsupportedText}:false;this.bodyCfg||(this.bodyCfg={tag:"div",cls:"x-panel-body",children:[{cls:"x-managed-iframe-mask",children:[Wtf.apply(Wtf.apply({tag:"iframe",frameborder:0,cls:"x-managed-iframe",style:this.frameStyle||null},this.frameConfig),unsup,Wtf.isIE&&Wtf.isSecure?{src:Wtf.SSL_SECURE_URL}:false),{tag:"img",src:this.shimUrl||Wtf.BLANK_IMAGE_URL,cls:this.shimCls}]}]});this.autoScroll=false;this.items=null;if(this.stateful!==false){this.stateEvents||(this.stateEvents=["documentloaded"])}Wtf.ux.ManagedIframePanel.superclass.initComponent.call(this);this.monitorResize||(this.monitorResize=this.fitToParent);this.addEvents({documentloaded:true,domready:true,message:true,exception:true});this.addListener=this.on},doLayout:function(){if(this.fitToParent&&!this.ownerCt){var pos=this.getPosition(),size=(Wtf.get(this.fitToParent)||this.getEl().parent()).getViewSize();this.setSize(size.width-pos[0],size.height-pos[1])}Wtf.ux.ManagedIframePanel.superclass.doLayout.apply(this,arguments)},beforeDestroy:function(){if(this.rendered){if(this.tools){for(var k in this.tools){Wtf.destroy(this.tools[k])}}if(this.header&&this.headerAsText){var s;if(s=this.header.child("span")){s.remove()}this.header.update("")}Wtf.each(["iframe","shim","header","topToolbar","bottomToolbar","footer","loadMask","body","bwrap"],function(elName){if(this[elName]){if(typeof this[elName].destroy=="function"){this[elName].destroy()}else{Wtf.destroy(this[elName])}this[elName]=null;delete this[elName]}},this)}Wtf.ux.ManagedIframePanel.superclass.beforeDestroy.call(this)},onDestroy:function(){Wtf.Panel.superclass.onDestroy.call(this)},onRender:function(ct,position){Wtf.ux.ManagedIframePanel.superclass.onRender.call(this,ct,position);if(this.iframe=this.body.child("iframe.x-managed-iframe")){var El=Wtf.Element;var mode=El[this.hideMode.toUpperCase()]||"x-hide-nosize";Wtf.each([this[this.collapseEl],this.floating?null:this.getActionEl(),this.iframe],function(el){if(el){el.setVisibilityMode(mode)}},this);if(this.loadMask){this.loadMask=Wtf.apply({disabled:false,maskEl:this.body,hideOnReady:true},this.loadMask)}if(this.iframe=new Wtf.ux.ManagedIFrame(this.iframe,{loadMask:this.loadMask,showLoadIndicator:this.showLoadIndicator,disableMessaging:this.disableMessaging,style:this.frameStyle})){this.loadMask=this.iframe.loadMask;this.iframe.ownerCt=this;this.relayEvents(this.iframe,["blur","focus","unload","documentloaded","domready","exception","message"].concat(this._msgTagHandlers||[]));delete this._msgTagHandlers}this.getUpdater().showLoadIndicator=this.showLoadIndicator||false;var ownerCt=this.ownerCt;while(ownerCt){ownerCt.on("afterlayout",function(container,layout){var MIM=Wtf.ux.ManagedIFrame.Manager,st=false;Wtf.each(["north","south","east","west"],function(region){var reg;if((reg=layout[region])&&reg.splitEl){st=true;if(!reg.split._splitTrapped){reg.split.on("beforeresize",MIM.showShims,MIM);reg.split._splitTrapped=true}}},this);if(st&&!this._splitTrapped){this.on("resize",MIM.hideShims,MIM);this._splitTrapped=true}},this,{single:true});ownerCt=ownerCt.ownerCt}}this.shim=Wtf.get(this.body.child("."+this.shimCls))},toggleShim:function(){if(this.shim&&this.shimCls){this.shim.toggleClass(this.shimCls+"-on")}},afterRender:function(container){var html=this.html;delete this.html;Wtf.ux.ManagedIframePanel.superclass.afterRender.call(this);if(this.iframe){if(this.defaultSrc){this.setSrc()}else{if(html){this.iframe.update(typeof html=="object"?Wtf.DomHelper.markup(html):html)}}}},sendMessage:function(){if(this.iframe){this.iframe.sendMessage.apply(this.iframe,arguments)}},on:function(name){var tagRE=/^message\:/i,n=null;if(typeof name=="object"){for(var na in name){if(!this.filterOptRe.test(na)&&tagRE.test(na)){n||(n=[]);n.push(na.toLowerCase())}}}else{if(tagRE.test(name)){n=[name.toLowerCase()]}}if(this.getFrame()&&n){this.relayEvents(this.iframe,n)}else{this._msgTagHandlers||(this._msgTagHandlers=[]);if(n){this._msgTagHandlers=this._msgTagHandlers.concat(n)}}Wtf.ux.ManagedIframePanel.superclass.on.apply(this,arguments)},setSrc:function(url,discardUrl,callback){url=url||this.defaultSrc||false;if(!url){return this}if(url.url){callback=url.callback||false;discardUrl=url.discardUrl||false;url=url.url||false}var src=url||(Wtf.isIE&&Wtf.isSecure?Wtf.SSL_SECURE_URL:"");if(this.rendered&&this.iframe){this.iframe.setSrc(src,discardUrl,callback)}return this},getState:function(){var URI=this.iframe?this.iframe.getDocumentURI()||null:null;return Wtf.apply(Wtf.ux.ManagedIframePanel.superclass.getState.call(this)||{},URI?{defaultSrc:typeof URI=="function"?URI():URI}:null)},getUpdater:function(){return this.rendered?(this.iframe||this.body).getUpdater():null},getFrame:function(){return this.rendered?this.iframe:null},getFrameWindow:function(){return this.rendered&&this.iframe?this.iframe.getWindow():null},getFrameDocument:function(){return this.rendered&&this.iframe?this.iframe.getDocument():null},getFrameDoc:function(){return this.rendered&&this.iframe?this.iframe.getDoc():null},getFrameBody:function(){return this.rendered&&this.iframe?this.iframe.getBody():null},load:function(loadCfg){var um;if(um=this.getUpdater()){if(loadCfg&&loadCfg.renderer){um.setRenderer(loadCfg.renderer);delete loadCfg.renderer}um.update.apply(um,arguments)}return this},doAutoLoad:function(){this.load(typeof this.autoLoad=="object"?this.autoLoad:{url:this.autoLoad})}});Wtf.reg("iframepanel",Wtf.ux.ManagedIframePanel);Wtf.ux.ManagedIframePortlet=Wtf.extend(Wtf.ux.ManagedIframePanel,{anchor:"100%",frame:true,collapseEl:"bwrap",collapsible:true,draggable:true,cls:"x-portlet"});Wtf.reg("iframeportlet",Wtf.ux.ManagedIframePortlet);Wtf.apply(Wtf.Element.prototype,{setVisible:function(visible,animate){if(!animate||!Wtf.lib.Anim){if(this.visibilityMode==Wtf.Element.DISPLAY){this.setDisplayed(visible)}else{if(this.visibilityMode==Wtf.Element.VISIBILITY){this.fixDisplay();this.dom.style.visibility=visible?"visible":"hidden"}else{this[visible?"removeClass":"addClass"](String(this.visibilityMode))}}}else{var dom=this.dom;var visMode=this.visibilityMode;if(visible){this.setOpacity(0.01);this.setVisible(true)}this.anim({opacity:{to:(visible?1:0)}},this.preanim(arguments,1),null,0.35,"easeIn",function(){if(!visible){if(visMode==Wtf.Element.DISPLAY){dom.style.display="none"}else{if(visMode==Wtf.Element.VISIBILITY){dom.style.visibility="hidden"}else{Wtf.get(dom).addClass(String(visMode))}}Wtf.get(dom).setOpacity(1)}})}return this},isVisible:function(deep){var vis=!(this.getStyle("visibility")=="hidden"||this.getStyle("display")=="none"||this.hasClass(this.visibilityMode));if(deep!==true||!vis){return vis}var p=this.dom.parentNode;while(p&&p.tagName.toLowerCase()!="body"){if(!Wtf.fly(p,"_isVisible").isVisible()){return false}p=p.parentNode}return true}});Wtf.onReady(function(){var CSS=Wtf.util.CSS,rules=[];CSS.getRule(".x-managed-iframe")||(rules.push(".x-managed-iframe {height:100%;width:100%;overflow:auto;}"));CSS.getRule(".x-managed-iframe-mask")||(rules.push(".x-managed-iframe-mask{width:100%;height:100%;position:relative;}"));if(!CSS.getRule(".x-frame-shim")){rules.push(".x-frame-shim {z-index:9000;position:absolute;top:0px;left:0px;background:transparent!important;overflow:hidden;display:none;}");rules.push(".x-frame-shim-on{width:100%;height:100%;display:block;zoom:1;}");rules.push(".ext-ie6 .x-frame-shim{margin-left:5px;margin-top:3px;}")}CSS.getRule(".x-hide-nosize")||(rules.push(".x-hide-nosize,.x-hide-nosize object,.x-hide-nosize iframe{height:0px!important;width:0px!important;border:none;}"));if(!!rules.length){CSS.createStyleSheet(rules.join(" "))}})})()
