import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BacklogComponent } from './backlog/backlog.component';
import { TasksComponent } from './tasks/tasks.component';
import { PlanificationComponent } from './planification/planification.component';
import { SprintActifComponent } from './sprint-actif/sprint-actif.component';
import { TestsComponent } from './tests/tests.component';
import { ReleasesComponent } from './releases/releases.component';
import { DocumentationComponent } from './documentation/documentation.component';
import { StatisticsComponent } from './statistics/statistics.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';


@NgModule({
  declarations: [
    AppComponent,
    BacklogComponent,
    TasksComponent,
    PlanificationComponent,
    SprintActifComponent,
    TestsComponent,
    ReleasesComponent,
    DocumentationComponent,
    StatisticsComponent,
    PageNotFoundComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
