import './App.css';

function App() {
  return (
  <div className="app"> 
  
  {/* Top Navigation Bar */}
  <header className="top-nav">
    <div className="company">
      <img src="/imogene-logo.svg" alt="Imogene Logo" className="company-icon" />
    </div>



    <div className="quick-actions">
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
        <button className="tool-bttn">Generate Random</button>
        <button className="tool-bttn">Generate Colour</button>
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



      {/* Center Canvas Area */}
      <main className="canvas-area">
        <div className="canvas-container">
          <p>Canvas will go here</p>
        </div>
      </main>





      {/* Right Settings Panel */}
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
                defaultValue={133}
                min="50"
                max="1000"
              />
            </div>

            <div className="form-section">
              <label htmlFor="height">Height (px)</label>
              <input 
                type="number"
                id="height"
                defaultValue={100}
                min="50"
                max="1000"
              />
            </div>
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
                defaultValue={50}
                min="10"
                max="200"
              />
            </div>

            <div className="form-section">
              <label htmlFor="generations">Generations</label>
              <input
                type="number"
                id="generations"
                defaultValue={100}
                min="10"
                max="1000"
              />
            </div>
            <button className="ga-bttn">Run GA</button>
          </div>
        </div>
      </aside>

    </div>
  </div>
  )
} 

export default App;