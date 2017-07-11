var firebase = require("firebase");
var express = require('express');
var app = express();
var fs = require('fs');
var readline = require('readline');
var router = require('./router/main')(app);
var bodyParser = require('body-parser');

app.set('views', './views');
app.set('view engine', 'jade');
//app.set('view engine', 'ejs');
app.engine('html', require('ejs').renderFile);
//app.engine('new',require('ejs').renderFile);
app.use(express.static('public'));
app.use(bodyParser.urlencoded({ extended:false }));


var auth,database,userInfo,selectedKey;
var files = [];
var config = {
  apiKey: "AIzaSyAhu6_WkRCOz09jHNc1drJcUhNDTxbkJJc",
     authDomain: "dictionarydb.firebaseapp.com",
     databaseURL: "https://dictionarydb.firebaseio.com",
     projectId: "dictionarydb",
     storageBucket: "dictionarydb.appspot.com",
     messagingSenderId: "340960254040"
};
firebase.initializeApp(config);
// Get a reference to the database service
database = firebase.database()  ;

app.listen(3000, function(){
  console.log('Connected, 3000 port!');
})
function processFile(filename) {
    var instream = fs.createReadStream(filename);
    var reader = readline.createInterface(instream, process.stdout);
  //  var wordRef = database.ref('word/');
    var count = 0;
    // 한 줄씩 읽어들인 후에 발생하는 이벤트
    //for()
    reader.on('line', function(line){
      //const ulList = document.getElementById('dictionary');
      var word_string = line.trim();
      var key_word = word_string.substr(0,1);
      console.log(word_string+" "+key_word);
      var wordRef = database.ref('word/'+key_word+'/'+word_string).set(true);
    });
    // 모두 읽어들였을 때 발생하는 이벤트
    reader.on('close', function(line) {
        console.log('파일을 모두 읽음.');
    });
}

// 함수 실행
var filename = './data/tt.txt';
//processFile(filename);
app.get('/topic/new',function(req, res){
    res.render('new',{topics:files});
});
app.post('/topic/email',function(req,res){
  var id = req.body.id;
  var email = req.body.email;
  if(id == '' ) {
    return;// 빈데이터 생성 방지
  }
  if(email){
    var emailRef = database.ref('user/'+id);
  emailRef.update({
    id : id,
    email : email
  })
  }
  else {
    emailRef.push({
      id : id,
      email : email
    })
  }
  console.log("Complete Database update");
});

app.post('/topic/new',function(req,res,err) {

  var description = req.body.description;
  if(description == 'GG')
  {
    files = [];
    res.redirect('/topic/new');
  }
  if(files.length != 0)
  {
    console.log('here');
    var last_word = files[files.length - 1];
    var word = last_word.substr(last_word.length-1,last_word.length);
  }
  else {
    var word = description.substr(0,1);
  }
  var input_word = description.substr(0,1);
  console.log('last_word: '+last_word);
  console.log('word: '+word);
  console.log('input_word: '+input_word);
  if(word == input_word || files.length == 0)
  {
      var result;
      checkDB(description, function(result){
        if(result)
        {
            var result2;
            var word_list = [];
            reply_word(description, word_list, function(result2){
              if(result2){
                var ranNumword = generateRandom(0,word_list.length);
                var mm = word_list[ranNumword];
                //console.log('mm: '+mm);
                files.push(description);
                files.push(mm);
                if(mm != undefined && description != undefined){
                  res.redirect('/topic/new');
                }
                //res.redirect('/topic/new');
              }else{
                //alert('승리!');
                console.log('승리!');
              }
            });
            //console.log('des: '+description);
            //응답하는 단어선택
        }
        else{
          console.log('No word');
          //사전에 없는 단어 에러메세지 출력
        }
    });
  }
  else {
    console.log(err);
    res.redirect('/topic/new');
  }
});
function checkDB(description, callback)
{
  var key_word = description.substr(0,1);
  var dictionaryRef = database.ref('word/'+key_word+'/'+description);
  dictionaryRef.once('value', function(snapshot){
    if(snapshot.val() == true) callback(true);
    else callback(false);
  });
}
function reply_word(description, word_list, callback){
  var key_word = description.substr(description.length-1,description.length);
  var dictionaryRef = database.ref('word/'+key_word);
  dictionaryRef.once('value', function(snapshot){
      if(snapshot.numChildren() == 0) callback(false);
        snapshot.forEach(function(data){
        word_list.push(data.key);//리스트에 삽입
        if(snapshot.numChildren() == word_list.length){
          callback(true);
        }
      });
    });
  }
  function on_chiled_added(data){
    var id = data.id;
    var email = data.email;

  }
  var generateRandom = function (min, max) {
    var ranNum = Math.floor(Math.random()*(max-min+1)) + min;
    return ranNum;
}
