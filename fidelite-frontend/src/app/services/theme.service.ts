import { inject, Injectable, signal } from '@angular/core';
import { DOCUMENT } from '@angular/common';

export type ColorScheme = 'light' | 'dark';

const STORAGE_KEY = 'staff-appearance';
const DEFAULT_PRIMARY = '#F97316';

const BASE_VARS: Record<ColorScheme, Record<string, string>> = {
  light: {
    'sidebar':    '#1B2A4A',
    'surface':    '#FFFFFF',
    'bg':         '#F8F9FA',
    'text':       '#1C1C1C',
    'text-muted': '#6B7280',
    'border':     '#E5E7EB',
    'success':    '#22C55E',
    'danger':     '#EF4444',
  },
  dark: {
    'sidebar':      '#060F1E',
    'surface':      '#142030',
    'bg':           '#0D1B2A',
    'text':         '#E0EAF5',
    'text-muted':   '#8090A8',
    'border':       '#1C3050',
    'success':      '#4ADE80',
    'danger':       '#F87171',
    'shadow-color': 'rgba(0, 0, 0, 0.35)',
  },
};

const ALL_OVERRIDABLE = [
  '--primary', '--primary-dark', '--primary-light',
  '--sidebar', '--surface', '--bg', '--text', '--text-muted',
  '--border', '--success', '--danger', '--shadow-color', '--table-hover',
];

function hexToRgb(hex: string): [number, number, number] {
  const n = parseInt(hex.replace('#', ''), 16);
  return [(n >> 16) & 255, (n >> 8) & 255, n & 255];
}

function rgbToHex(r: number, g: number, b: number): string {
  return '#' + [r, g, b]
    .map(c => Math.round(Math.max(0, Math.min(255, c))).toString(16).padStart(2, '0'))
    .join('');
}

@Injectable({ providedIn: 'root' })
export class ThemeService {
  private readonly _doc = inject(DOCUMENT);

  readonly scheme = signal<ColorScheme>('light');
  readonly primary = signal<string>(DEFAULT_PRIMARY);

  constructor() {
    this._load();
  }

  setScheme(s: ColorScheme): void {
    this.scheme.set(s);
    this._apply();
    this._save();
  }

  setPrimary(hex: string): void {
    this.primary.set(hex);
    this._apply();
    this._save();
  }

  private _apply(): void {
    const root = this._doc.documentElement;
    ALL_OVERRIDABLE.forEach(v => root.style.removeProperty(v));

    const hex = this.primary();
    const [r, g, b] = hexToRgb(hex);
    const s = this.scheme();

    Object.entries(BASE_VARS[s]).forEach(([k, v]) => root.style.setProperty(`--${k}`, v));

    root.style.setProperty('--primary', hex);
    root.style.setProperty('--primary-dark', rgbToHex(r * 0.88, g * 0.88, b * 0.88));

    if (s === 'dark') {
      root.style.setProperty('--primary-light', `rgba(${r}, ${g}, ${b}, 0.18)`);
      root.style.setProperty('--table-hover', `color-mix(in srgb, ${hex} 12%, #142030)`);
    } else {
      root.style.setProperty('--primary-light', rgbToHex(r * 0.12 + 255 * 0.88, g * 0.12 + 255 * 0.88, b * 0.12 + 255 * 0.88));
    }
  }

  private _save(): void {
    localStorage.setItem(STORAGE_KEY, JSON.stringify({ scheme: this.scheme(), primary: this.primary() }));
  }

  private _load(): void {
    try {
      const saved = JSON.parse(localStorage.getItem(STORAGE_KEY) ?? '{}') as Partial<{ scheme: ColorScheme; primary: string }>;
      if (saved.primary) this.primary.set(saved.primary);
      if (saved.scheme === 'dark' || saved.scheme === 'light') this.scheme.set(saved.scheme);
    } catch { /* ignore corrupted storage */ }
    this._apply();
  }
}
