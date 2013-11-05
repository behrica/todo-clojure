describe('todo homepage', function() {
  it('test first todos present in list', function() {
    browser.get('http://localhost:8080/index.html');
    var todos = by.repeater('todo in todos');
    expect(element(todos.row(0)).getText()).toEqual("newTitle - 2020-01-01");
    expect(element(todos.row(1)).getText()).toEqual("newTitle - 2020-01-02");

      var input = by.input("todoTitle");
      element(input).sendKeys("submittedNewTitle");
    element(by.name("submit")).click();

    expect(element(todos.row(2)).getText()).toEqual("submittedNewTitle - 2012-01-01");

  });
});
