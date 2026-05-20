import { Component } from '@angular/core';
import { RouterOutlet, RouterModule } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterModule],
  template: `
    <main>
      <nav class="glass-nav">
        <div class="logo">Backend <span>Avançado</span></div>
        <div class="nav-links">
          <a routerLink="/authors">Autores</a>
          <a routerLink="/books">Livros</a>
        </div>
      </nav>
      
      <router-outlet></router-outlet>
      
      <footer class="footer">
        <p>LABORATÓRIO DE BACKEND AVANÇADO - SPRING BOOT</p>
      </footer>
    </main>
  `,
  styles: [`
    .glass-nav {
      background: rgba(255, 255, 255, 0.05);
      backdrop-filter: blur(10px);
      padding: 1rem 5%;
      display: flex;
      justify-content: space-between;
      align-items: center;
      border-bottom: 1px solid rgba(255, 255, 255, 0.1);
    }
    .nav-links a {
      color: white;
      text-decoration: none;
      margin-left: 20px;
      font-size: 0.9rem;
      opacity: 0.7;
      transition: opacity 0.3s;
    }
    .nav-links a:hover {
      opacity: 1;
    }
    .logo {
      font-weight: 800;
      letter-spacing: 2px;
    }
    .logo span { color: #818cf8; }
    .footer {
      position: fixed;
      bottom: 0;
      width: 100%;
      text-align: center;
      padding: 1rem;
      font-size: 0.7rem;
      color: #475569;
      letter-spacing: 3px;
      background: rgba(15, 23, 42, 0.9);
    }
  `]
})
export class AppComponent {
  title = 'frontend';
}
