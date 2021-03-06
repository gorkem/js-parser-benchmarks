var acorn = require('acorn/dist/acorn_loose');

var Main = function () {
       function Main() {
         this.buffer="";
       }
       Main.prototype.run = function () {
           var _this = this;
           var readline = require("readline");
           var rl = readline.createInterface(process.stdin, process.stdout);
           rl.on("line", function (line) {
               if(line === 'END'){
                 _this.processRequest();
              }else{
                _this.buffer += line;
              }
           });
       };

       Main.prototype.processRequest = function () {
           try {
               var options =  {"locations":true,
                                "range":true};
               var result = acorn.parse_dammit(this.buffer, options);
               if (result === undefined) {
                   result = null;
               }
               var resultJson = JSON.stringify(result);
               this.buffer = null;
               console.log("RESULT: " + resultJson);
           }
           catch (e) {
               var error;
               if (e.stack != null) {
                   error = e.stack;
               }
               else if (e.message != null) {
                   error = e.message;
               }
               else {
                   error = "Error: No stack trace or error message was provided.";
               }
               console.log("ERROR: " + error.replace(/\n/g, "\\n"));
           }
       };
       return Main;
   }();

var main = new Main();
main.run();

