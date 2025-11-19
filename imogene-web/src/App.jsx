
import { useState, useRef, useEffect } from 'react';
import './App.css';

function App() {

  /* Theme state - defaults to light, checks localStorage, or system preference */
  const [theme, setTheme] = useState(() => {
    const savedTheme = localStorage.getItem('theme');
    if (savedTheme) return savedTheme;

    // Check system preference
    if (window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches) {
      return 'dark';
    }
    return 'light';
  });

  /* States for canvas dimensions */
  const [canvasWidth, setCanvasWidth] = useState(200);
  const [canvasHeight, setCanvasHeight] = useState(200);

  const [inputWidth, setInputWidth] = useState("200");
  const [inputHeight, setInputHeight] = useState("200");
  const [upscale, setUpscale] = useState(2); // Default 2x

  const canvasRef = useRef(null);

  // State for current image
  const [currentImage, setCurrentImage] = useState(null);
  const [lastGenerationType, setLastGenerationType] = useState(null);

  // API URL constant
  const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080";

  // Generation function selection states:
  const [generationFunction, setGenerationFunction] = useState("randomPixels")
  const [fitnessFunction, setFitnessFunction] = useState("checkerBoard")
  const [selectionFunction, setSelectionFunction] = useState("rouletteWheel")
  const [crossoverFunction, setCrossoverFunction] = useState("pixelwiseRGB")
  const [mutationFunction, setMutationFunction] = useState("randomPixelsRandomisation")


  // Apply theme to document and save to localStorage
  useEffect(() => {
    document.documentElement.setAttribute('data-theme', theme);
    localStorage.setItem('theme', theme);
  }, [theme]);

  // Redraws to new upscale - upscale is auto applied.
  useEffect(() => {
    if (currentImage) {
      renderCanvas(currentImage);
    }
  }, [upscale]);

  /* Toggle between light and dark theme */
  function toggleTheme() {
    setTheme(prevTheme => prevTheme === 'light' ? 'dark' : 'light');
  }


  /* Canvas dimensions change function. */
  function handleApplyCanvasSize() {

    const widthNum = Number(inputWidth);
    const heightNum = Number(inputHeight);

    // Constraint for invalid or empty input
    if (isNaN(widthNum) || isNaN(heightNum)) return;

    const safeWidth = Math.min(Math.max(widthNum, 50), 1000);
    const safeHeight = Math.min(Math.max(heightNum, 50), 1000);

    setCanvasWidth(safeWidth);
    setCanvasHeight(safeHeight);

    // Regenerates last image with new dimensions.
    if (currentImage && lastGenerationType) {
      handleGeneration(lastGenerationType, safeWidth, safeHeight);
    }
  }

  /* Converts backend RGB data to canvas format and renders it. */
  function renderCanvas(rgbData) {
    const canvas = canvasRef.current;
    if (!canvas) return;

    const canvasDrawing = canvas.getContext('2d');
    const height = rgbData.length;
    const width = rgbData[0].length;

    // Upscaled image dimensions
    canvas.width = width * upscale;
    canvas.height = height * upscale;


    for (let y = 0; y < height; y++) {
      for (let x = 0; x < width; x++) {
        const r = rgbData[y][x][0];
        const g = rgbData[y][x][1];
        const b = rgbData[y][x][2];

        canvasDrawing.fillStyle = `rgb(${r}, ${g}, ${b})`;
        // Draws upscaled pixel block at scaled coordinates
        canvasDrawing.fillRect(x * upscale, y * upscale, upscale, upscale);
      }
    }
  }


  /* Makes a GET request with canvas dimensions and generation type, receives RGB data, and renders it on the canvas. */
  async function handleGeneration(type, width = canvasWidth, height = canvasHeight) {
    try {
      setLastGenerationType(type);

      const response = await fetch(`${API_BASE_URL}/generate?width=${width}&height=${height}&type=${type}`)
      const rgbData = await response.json();

      // Saves image 
      setCurrentImage(rgbData);
      renderCanvas(rgbData);
    }
    catch (e) {
      console.error('Error generating image!', e);
    }
  }

  return (
  <div className="app"> 


  
  {/* Top Navigation Bar */}
  <header className="top-nav">
    <div className="company">
      <img src="/imogene-logo.svg" alt="Imogene Logo" className="company-icon" />
    </div>

    <div className="quick-actions">
      <button className="action-bttn" onClick={toggleTheme}>
        {theme === 'light' ? '🌙 Dark' : '☀️ Light'}
      </button>
      <button className="action-bttn">Undo</button>
      <button className="action-bttn">Redo</button>
      <button className="action-bttn save-action-bttn">Save</button>
    </div>
  </header>



    {/* Main Content Area. */}
    <div className="main-content">


      {/* Left Toolbar */}
      <aside className="left-toolbar">
        <h3>GENERATION</h3>
        <button className="tool-bttn" onClick={() => handleGeneration('randomBitmap')}>Generate Random</button>
        <button className="tool-bttn" onClick={() => handleGeneration('randomColour')}>Generate Colour</button>
        <button className="tool-bttn clear-bttn">Clear Canvas</button>
     

        {/* Filters Section */}
        <div className="toolbar-section">
          <h3 className="section-header">Filters</h3>
          <button className="tool-bttn">Grayscale</button>
          <button className="tool-bttn">Invert</button>
        </div>

        {/* Smoothing Section */}
        <div className="toolbar-section">
          <h3 className="section-header">Smoothing</h3>
          <button className="tool-bttn">～ Soft Blur</button>
          <button className="tool-bttn">≈ Medium Blur</button>
          <button className="tool-bttn">≋ Hard Blur</button>
        </div>

        {/* Color Rebalancing Section */}
        <div className="toolbar-section">
          <h3 className="section-header">Colour Rebalance</h3>
          <button className="tool-bttn">Rebalance Red</button>
          <button className="tool-bttn">Rebalance Green</button>
          <button className="tool-bttn">Rebalance Blue</button>
        </div>

        {/* Spectrum Effects Section */}
        <div className="toolbar-section">
          <h3 className="section-header">Spectrum Projections</h3>
          <button className="tool-bttn">Red → Green</button>
          <button className="tool-bttn">Green → Blue</button>
          <button className="tool-bttn">Blue → Red</button>
          <button className="tool-bttn">Hue → Saturation</button>
          <button className="tool-bttn">Saturation → Lightness</button>
          <button className="tool-bttn">Lightness → Hue</button>
        </div>
      </aside>



      {/* Canvas Area */}
      <main className="canvas-area">

        <div className="canvas-container">
          <canvas 
            ref={canvasRef}
            className="canvas-image"
            width={600}
            height={600}
          />
        </div>
      </main>



      {/* Right Canvas Settings Panel */}
      <aside className="right-panel">
        {/*Canvas Settings*/}
        <div className="settings-section">
          <h3 className="settings-header">Canvas Settings</h3>

          <div className="settings-content">
            <div className="form-section">
              <label htmlFor='width'>Width (px)</label>
              <input
                type="number"
                id="width"
                value={inputWidth}
                min="50"
                max="1000"
                onChange={(e) => setInputWidth((e.target.value))}
              />
            </div>

            <div className="form-section">
              <label htmlFor="height">Height (px)</label>
              <input 
                type="number"
                id="height"
                value={inputHeight}
                min="50"
                max="1000"
                onChange={(e) => setInputHeight((e.target.value))}
              />
            </div>

            <div className="form-section">
                <label htmlFor="upscale">Upscale</label>
                <input
                  type="number"
                  id="upscale"
                  value={upscale}
                  min="1"
                  max="50"
                  onChange={(e) => setUpscale(Number(e.target.value))}
                />
              </div>

            <button className="ga-bttn" onClick={handleApplyCanvasSize}>Apply</button>
          </div>
        </div>




        {/* Genetic Algorithm Settings */}
        <div className="settings-section">
          <h3 className="settings-header">Genetic Algorithm</h3>
          <div className="settings-content">

            <div className="form-section">
              <label htmlFor="population">Population Size</label>
              <input
                type="number"
                id="population"
                defaultValue={1000}
                min="1"
                max="1000"
              />
            </div>

            <div className="form-section">
              <label htmlFor="elite">Elite</label>
              <input
                type="number"
                id="generations"
                defaultValue={30}
                min="1"
                max="30"
              />
            </div>

            <div className="form-section">
              <label htmlFor="re-generation">Re-Generations</label>
              <input
                type="number"
                id="generations"
                defaultValue={50}
                min="1"
                max="50"
              />
            </div>

            <div className="form-section">
              <label htmlFor="generation-function">Generation Function</label>
              <select
                id="generation-function"
                value={generationFunction}
                onChange={(e) => setGenerationFunction(e.target.value)}
              >
                  <option value="randomPixels">Random Pixels</option>
                  <option value="randomColour">Random Colour</option>
              </select>
            </div>

            <div className="form-section">
              <label htmlFor="fitness-function">Fitness Function</label>
              <select
                id="fitness-function"
                value={fitnessFunction}
                onChange={(e) => setFitnessFunction(e.target.value)}
              >
                  <option value="checkerBoard">Checker Board</option>
                  <option value="simpleImageLikeness">Simple Image Likeness</option>
                  <option value="strongImageLikeness">Strong Image Likeness</option>
                  <option value="hueOnlyImageLikeness">Hue-Only Image Likeness</option>
                  <option value="lightnessOnlyImageLikeness">Lightness-Only Image Likeness</option>
              </select>
            </div>

            <div className="form-section">
              <label htmlFor="selection-function">Selection Function</label>
              <select
                id="selection-function"
                value={selectionFunction}
                onChange={(e) => setSelectionFunction(e.target.value)}
              >
                  <option value="rouletteWheel">Roulette Wheel</option>
                  <option value="tournament">Tournament</option>
              </select>
            </div>

            <div className="form-section">
              <label htmlFor="crossover-function">Crossover Function</label>
               <select
                id="crossover-function"
                value={crossoverFunction}
                onChange={(e) => setCrossoverFunction(e.target.value)}
              >
                  <option value="pixelwiseRGB">Pixelwise RGB</option>
                  <option value="pixelwiseHSL">Pixelwise HSL</option>
                  <option value="strongPixelwise">Strong Pixelwise</option>
              </select>
            </div>

            <div className="form-section">
              <label htmlFor="mutation-function">Mutation Function</label>
               <select
                id="mutation-function"
                value={mutationFunction}
                onChange={(e) => setMutationFunction(e.target.value)}
              >
                  <option value="randomPixelsRandomisation">Random Pixels Randomisation</option>
                  <option value="randomPixelsAdjustments">Random Pixels Adjustments</option>
                  <option value="strongPixelsAdjustments">Strong Pixels Adjustments</option>
              </select>
            </div>

            <button className="ga-bttn">Begin GA</button>
          </div>
        </div>
      </aside>

    </div>
  </div>
  )
} 

export default App;