describe('todo homepage', function() {
  it('test first todos present in list', function() {
    browser.get('http://localhost:8080/index.html');
    expect(element(by.repeater('todo in todos').row(0)).getText()).toEqual("newTitle - 2020-01-01");
    expect(element(by.repeater('todo in todos').row(1)).getText()).toEqual("newTitle - 2020-01-02");
  });
});
