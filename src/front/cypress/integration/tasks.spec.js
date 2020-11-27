context('Assertions', () => {
    beforeEach(() => {
        cy.visit('http://localhost:8080/project/1/tasks');
    });

    it('Should open and close task card', () => {
        cy.get('.card-title').first().click();
        cy.get('.modal-dialog').should('be.visible');
        cy.wait(1000);
        cy.get('app-expanded-task-card > .modal > .modal-dialog > .modal-content >.modal-header > .close').first().click();
        cy.get('.modal-dialog').should('not.be.visible');
    });

    it('Should open and close task creation', () => {
        cy.get('#newbuttontask').first().click();
        cy.get('form').should('be.visible');
        cy.wait(1000);
        cy.get('#newTask > .modal-dialog > .modal-content >.modal-header > .close').first().click();
        cy.get('form').should('not.be.visible');
    });


});
