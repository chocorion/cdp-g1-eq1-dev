context('Assertions', () => {
    beforeEach(() => {
        cy.visit('http://localhost:8080');
    });

    it('Should open and close popup', () => {
        cy.get('.add-project-button').click();
        cy.get('.project-modal').should('be.visible');
        cy.wait(1000);
        cy.get('.close-project-modal').click();
        cy.get('.project-modal').should('not.be.visible');
    });


});
