import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { BacklogComponent } from './backlog/backlog.component';
import { TasksComponent } from './tasks/tasks.component';
import { PlanificationComponent } from './planification/planification.component';
import { SprintActifComponent } from './sprint-actif/sprint-actif.component';
import { TestsComponent } from './tests/tests.component';
import { ReleasesComponent } from './releases/releases.component';
import { DocumentationComponent } from './documentation/documentation.component';
import { StatisticsComponent } from './statistics/statistics.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { ProjectComponent } from './project/project.component';


const routes: Routes = [
  { path: 'backlog'      , component: BacklogComponent },
  { path: 'tasks'        , component: TasksComponent },
  { path: 'planification', component: PlanificationComponent },
  { path: 'sprintActif'  , component: SprintActifComponent },
  { path: 'tests'        , component: TestsComponent },
  { path: 'releases'     , component: ReleasesComponent },
  { path: 'documentation', component: DocumentationComponent },
  { path: 'statistics'   , component: StatisticsComponent },
  { path: 'project'      , component: ProjectComponent},
  { path: ''             , redirectTo: '/project', pathMatch: 'full'},
  { path: '**'           , component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
