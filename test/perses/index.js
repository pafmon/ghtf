const fs = require('fs');
const readline = require('readline');
const path = require('path');
const yaml = require('js-yaml');

values=0
items=0

////////////// LOAD PERSES CONFIGURATION FILE ///////////////////

var rootPath=path.normalize(".");;

var configFileName = rootPath+"/perses-config.yml";
var config = {};


//Load first device log file

var resultFileName = rootPath+"/devices-logs/log-android1.txt";

console.log("Loading Perses Config ("+configFileName+").");
try {
    config = yaml.safeLoad(fs.readFileSync(configFileName, 'utf8'));

} catch (e) {
  console.error(e);
  process.exit(1);
}



//////////////

const fileStream = fs.createReadStream(resultFileName);

 readInterface = readline.createInterface({
    input: fileStream,
    console: false
});

//Heatmap-Log
readInterface.on('line', function(line) {
    lineSplit = line.split(",");
    values += Number(lineSplit[lineSplit.length-1]);
    items++;
});

readInterface.on('close', function(){
    avg = Number((values/items).toFixed(3));
    
    if (avg > config.max_avg_devices){
        console.error("TEST FAILED: result_device1 > max_avg_devices --> "+avg+ " > "+config.max_avg_devices);
        process.exit(1);
      }else{
        console.log("TEST PASSED: result_device1 > max_avg_devices --> "+avg+ " > "+config.max_avg_devices);
      }
});


