import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BacklogComponent} from './backlog/backlog.component';
import {TasksComponent} from './tasks/tasks.component';
import {PlanificationComponent} from './planification/planification.component';
import {SprintActifComponent} from './sprint-actif/sprint-actif.component';
import {TestsComponent} from './tests/tests.component';
import {ReleasesComponent} from './releases/releases.component';
import {DocumentationComponent} from './documentation/documentation.component';
import {StatisticsComponent} from './statistics/statistics.component';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';
import {ProjectService} from './services/project.service';
import {HttpClientModule} from '@angular/common/http';
import {ProjectComponent} from './project/project.component';
import {ProjectSearchComponent} from './project/project-search/project-search.component';
import {ProjectListComponent} from './project/project-list/project-list.component';
import {ProjectItemComponent} from './project/project-item/project-item.component';
import {SidebarComponent} from './sidebar/sidebar.component';
import { TestItemComponent } from './tests/test-item/test-item.component';
import { FormsModule, ReactiveFormsModule} from '@angular/forms';

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
        PageNotFoundComponent,
        ProjectComponent,
        ProjectSearchComponent,
        ProjectListComponent,
        ProjectItemComponent,
        SidebarComponent,
        TestItemComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule
    ],
    providers: [
        ProjectService
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
