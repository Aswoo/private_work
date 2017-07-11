module.exports = function(app)
{
  app.get('/topic/popup',function(req,res){
  console.log("pop");
  res.render('popup');
});
}
