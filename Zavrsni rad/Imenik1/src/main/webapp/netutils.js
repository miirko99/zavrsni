
function get(from,params,callback) {
    if(params instanceof Function && callback==null) {
        callback=params;
        params=null;
    }
    axios.get(from,{params})
       .then((response) => {
           if(callback!=null)
              callback.call(this,response.data);
       })
       .catch((e) => {
           processError(e);
       })
 }

 function post(from, params, callback) {
    _post('application/json',from,params,callback);
 }

 function postMP(from,params,callback) {
    if(params!=null && !(params instanceof Function)) {
       let data=new FormData();
       Object.keys(params).forEach((key)=> { data.append(key,params[key]) });
       params = data;
    }
    _post('multipart/form-data',from,params,callback);
 }

 function _post(contentType, from,params,callback) {
    if(params instanceof Function && callback==null) {
        callback=params;
        params=null;
    }
    let headers={
    	'Content-Type': contentType
  	}
    axios.post(from,params, headers)
       .then((response) => {
           if(callback!=null)
              callback.call(this,response.data);
       })
       .catch((e) => {
           processError(e);
       })
 }

function processError(e) {
   let skipLog=false;
   if(e.response.status==401) {
       if("redirect" in e.response.data) {
            router.push(e.response.data.redirect);
            skipLog=true;
       }
   }
   if(skipLog==true) {
       console.log(e);
   }
}

 function hex(byteArray) {
    return Array.from(byteArray, function(byte) {
        return ('0' + (byte & 0xFF).toString(16)).slice(-2);
    }).join('')
 }