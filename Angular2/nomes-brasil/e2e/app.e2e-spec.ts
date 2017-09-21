import { NomesBrasilPage } from './app.po';

describe('nomes-brasil App', function() {
  let page: NomesBrasilPage;

  beforeEach(() => {
    page = new NomesBrasilPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
