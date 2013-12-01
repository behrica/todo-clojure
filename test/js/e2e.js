describe('todo homepage', function() {
  it('test first todos present in list', function() {
    browser.get('http://localhost:8080/index.html');
    var todos = by.repeater('todo in todos');
    expect(element(todos.row(0)).getText()).toEqual("newTitle - Wed Jan 01 00:00:00 UTC 2020");
    expect(element(todos.row(1)).getText()).toEqual("newTitle - Thu Jan 02 00:00:00 UTC 2020");

      var input = by.input("todoTitle");
      element(input).sendKeys("submittedNewTitle");
    element(by.name("submit")).click();

    // todo: make test work
    // expect(element(todos.row(2)).getText()).toEqual("submittedNewTitle - Sun Dec 01 00:12:36 UTC 2013");

  });
});
