## Includes

Includes = {
	"constants.fxh"
	"standardfuncsgfx.fxh"
	"pdxmap.fxh"
	"shadow.fxh"
}


## Samplers

PixelShader = 
{
	Samplers = 
	{
		HeightTexture = 
		{
			AddressV = "Wrap"
			MagFilter = "Linear"
			AddressU = "Wrap"
			Index = 0
			MipFilter = "Linear"
			MinFilter = "Linear"
		}

		WaterNormal = 
		{
			AddressV = "Wrap"
			MagFilter = "Linear"
			AddressU = "Wrap"
			Index = 1
			MipFilter = "Linear"
			MinFilter = "Linear"
		}

		ReflectionCubeMap = 
		{
			AddressV = "Mirror"
			MagFilter = "Linear"
			Type = "Cube"
			AddressU = "Mirror"
			Index = 2
			MipFilter = "Linear"
			MinFilter = "Linear"
		}

		WaterColor = 
		{
			AddressV = "Wrap"
			MagFilter = "Linear"
			AddressU = "Wrap"
			Index = 3
			MipFilter = "Linear"
			MinFilter = "Linear"
		}

		WaterNoise = 
		{
			AddressV = "Wrap"
			MagFilter = "Linear"
			AddressU = "Wrap"
			Index = 4
			MipFilter = "Linear"
			MinFilter = "Linear"
		}

		WaterRefraction = 
		{
			AddressV = "Clamp"
			MagFilter = "Linear"
			AddressU = "Clamp"
			Index = 5
			MipFilter = "Linear"
			MinFilter = "Linear"
		}

		IceDiffuse = 
		{
			AddressV = "Wrap"
			MagFilter = "Linear"
			AddressU = "Wrap"
			Index = 6
			MipFilter = "Linear"
			MinFilter = "Linear"
		}

		IceNormal = 
		{
			AddressV = "Wrap"
			MagFilter = "Linear"
			AddressU = "Wrap"
			Index = 7
			MipFilter = "Linear"
			MinFilter = "Linear"
		}

		FoWTexture = 
		{
			AddressV = "Wrap"
			MagFilter = "Linear"
			AddressU = "Wrap"
			Index = 8
			MipFilter = "Linear"
			MinFilter = "Linear"
		}

		FoWDiffuse = 
		{
			AddressV = "Wrap"
			MagFilter = "Linear"
			AddressU = "Wrap"
			Index = 9
			MipFilter = "Linear"
			MinFilter = "Linear"
		}

		ShadowMap = 
		{
			AddressV = "Wrap"
			MagFilter = "Linear"
			AddressU = "Wrap"
			Index = 10
			MipFilter = "Linear"
			MinFilter = "Linear"
		}

		TITexture = 
		{
			AddressV = "Wrap"
			MagFilter = "Linear"
			AddressU = "Wrap"
			Index = 11
			MipFilter = "Linear"
			MinFilter = "Linear"
		}

		ProvinceColorMap = 
		{
			AddressV = "Wrap"
			MagFilter = "Linear"
			AddressU = "Wrap"
			Index = 12
			MipFilter = "Linear"
			MinFilter = "Linear"
		}
		
		IndirectionMap =
		{
			AddressV = "Clamp"
			MagFilter = "Point"
			AddressU = "Clamp"
			Index = 13
			MipFilter = "Linear"
			MinFilter = "Linear"
		}
	}
}


## Vertex Structs

VertexStruct VS_INPUT_WATER
{
    float2 position			: POSITION;
};


VertexStruct VS_OUTPUT_WATER
{
    float4 position			: PDX_POSITION;
	float3 pos				: TEXCOORD0; 
	float2 uv				: TEXCOORD1;
	float4 screen_pos		: TEXCOORD2; 
	float3 cubeRotation     : TEXCOORD3;
	float4 vShadowProj     : TEXCOORD4;	
	float4 vScreenCoord		: TEXCOORD5;
	float2 uv_ice			: TEXCOORD6;	
};

VertexStruct VS_INPUT_LAKE
{
    float4 position			: POSITION;
};

## Constant Buffers

ConstantBuffer( 2, 48 )
{
	float3 vTime_HalfPixelOffset;
}

## Shared Code

## Vertex Shaders

VertexShader = 
{
	MainCode VertexShader
	[[
		VS_OUTPUT_WATER main( const VS_INPUT_WATER VertexIn )
		{
			VS_OUTPUT_WATER VertexOut;
			VertexOut.pos = float3( VertexIn.position.x, WATER_HEIGHT, VertexIn.position.y );
			VertexOut.position = mul( ViewProjectionMatrix, float4( VertexOut.pos.x, VertexOut.pos.y, VertexOut.pos.z, 1.0f ) );
			VertexOut.screen_pos = VertexOut.position;
			VertexOut.screen_pos.y = FLIP_SCREEN_POS( VertexOut.screen_pos.y );
			VertexOut.uv = float2( ( VertexIn.position.x + 0.5f ) / MAP_SIZE_X,  ( VertexIn.position.y + 0.5f - MAP_SIZE_Y ) / -MAP_SIZE_Y );
			VertexOut.uv *= float2( MAP_POW2_X, MAP_POW2_Y ); //POW2
			VertexOut.uv_ice = float2(0,0);
			VertexOut.cubeRotation = float3(0,0,0);
			
			VertexOut.vShadowProj = mul( ShadowMapTextureMatrix, float4( VertexOut.pos, 1.0f ) );	
			
			// Output the screen-space texture coordinates
			VertexOut.vScreenCoord.x = ( VertexOut.position.x * 0.5 + VertexOut.position.w * 0.5 );
			VertexOut.vScreenCoord.y = ( VertexOut.position.w * 0.5 - VertexOut.position.y * 0.5 );
		#ifdef PDX_OPENGL
			VertexOut.vScreenCoord.y = -VertexOut.vScreenCoord.y;
		#endif			
			VertexOut.vScreenCoord.z = VertexOut.position.w;
			VertexOut.vScreenCoord.w = VertexOut.position.w;	
			
			return VertexOut;
		}
	]]
	
	MainCode VertexShaderLake
	[[
		VS_OUTPUT_WATER main( const VS_INPUT_LAKE VertexIn )
		{
			VS_OUTPUT_WATER VertexOut;
			VertexOut.pos = float3( VertexIn.position.x, VertexIn.position.z, VertexIn.position.y );
			VertexOut.position = mul( ViewProjectionMatrix, float4( VertexOut.pos.x, VertexOut.pos.y, VertexOut.pos.z, 1.0f ) );
			VertexOut.screen_pos = VertexOut.position;
			VertexOut.screen_pos.y = FLIP_SCREEN_POS( VertexOut.screen_pos.y );
			VertexOut.uv = float2( ( VertexIn.position.x + 0.5f ) / MAP_SIZE_X,  ( VertexIn.position.y + 0.5f - MAP_SIZE_Y ) / -MAP_SIZE_Y );
			VertexOut.uv *= float2( MAP_POW2_X, MAP_POW2_Y ); //POW2
			VertexOut.uv_ice = float2(0,0);
			VertexOut.cubeRotation = float3(0,0,0);
			
			VertexOut.vShadowProj = mul( ShadowMapTextureMatrix, float4( VertexOut.pos, 1.0f ) );	
			
			// Output the screen-space texture coordinates
			VertexOut.vScreenCoord.x = ( VertexOut.position.x * 0.5 + VertexOut.position.w * 0.5 );
			VertexOut.vScreenCoord.y = ( VertexOut.position.w * 0.5 - VertexOut.position.y * 0.5 );
		#ifdef PDX_OPENGL
			VertexOut.vScreenCoord.y = -VertexOut.vScreenCoord.y;
		#endif			
			VertexOut.vScreenCoord.z = VertexOut.position.w;
			VertexOut.vScreenCoord.w = VertexOut.position.w;	
			
			return VertexOut;
		}
	]]
}


## Pixel Shaders

PixelShader = 
{
	MainCode PixelShader
	[[
	
		float4 main( VS_OUTPUT_WATER Input ) : PDX_COLOR
		{
		#ifdef MAP_IGNORE_HEIGHT
			float waterHeight = 0.0f;
		#else
			float waterHeight = tex2D( HeightTexture, Input.uv ).x;
		#endif
			
			waterHeight /= ( 93.7f / 255.0f );
			waterHeight = saturate( ( waterHeight - 0.995f ) * 50.0f );

			float4 vFoWColor = GetFoWColor( Input.pos, FoWTexture);	
			float TI = GetTI( vFoWColor );	
			float4 vTIColor = float4(0.5255, 0.4902, 0.4627, 1);//GetTIColor( Input.pos, TITexture );

			if( ( TI - 0.99f ) * 1000.0f > 0.0f )
			{
				return float4( vTIColor.rgb, 1.0f - waterHeight );
			}
			
			float4 waterColor = tex2D( WaterColor, Input.uv );
			
			// Region colors (provinces)
			//float2 flippedUV = Input.uv;
			//flippedUV.y = 1.0f - flippedUV.y;
			//float4 vSample = tex2D( ProvinceColorMap, flippedUV );
			//waterColor.rgb = lerp( waterColor.rgb, vSample.rgb, saturate( vSample.a ) );

			float3 outColor = lerp( float3(0.23f, 0.28f, 0.46f), waterColor.rgb, 0.3f);	
			
			float vFoW = GetFoW( Input.pos, vFoWColor, FoWDiffuse );
			outColor = ApplyDistanceFog( outColor, Input.pos ) * vFoW;
			return float4( lerp( outColor, vTIColor.rgb, TI ), 1.0f - waterHeight );
		}
	]]
	
	MainCode PixelShaderLake
	[[
		float4 main( VS_OUTPUT_WATER Input ) : PDX_COLOR
		{
			float waterHeight = Input.pos.y - ( tex2D( HeightTexture, Input.uv ).x * 51.0 );
			waterHeight = ( 0.9f - waterHeight ) * 1.2f;

			float4 vFoWColor = GetFoWColor( Input.pos, FoWTexture);	
			float TI = GetTI( vFoWColor );	
			float4 vTIColor = float4(0.5255f, 0.4902f, 0.4627f, 1.0f);//GetTIColor( Input.pos, TITexture );

			if( ( TI - 0.99f ) * 1000.0f > 0.0f )
			{
				return float4( vTIColor.rgb, 1.0f - waterHeight );
			}
			
			float4 waterColor = tex2D( WaterColor, Input.uv );
			float3 outColor = lerp( float3(0.23f, 0.28f, 0.46f), waterColor.rgb, 0.3f);	
			
			float vFoW = GetFoW( Input.pos, vFoWColor, FoWDiffuse );
			outColor = ApplyDistanceFog( outColor, Input.pos ) * vFoW;
			return float4( lerp( outColor, vTIColor.rgb, TI ), 1.0f - waterHeight );
		}
	]]
	
	MainCode PixelShaderUnlit
	[[
		float4 main( VS_OUTPUT_WATER Input ) : PDX_COLOR
		{
			// Grab the shadow term
			float fShadowTerm = CalculateShadow( Input.vShadowProj, ShadowMap);		
			return float4( fShadowTerm, fShadowTerm, fShadowTerm, 1.0f );
		}
	]]

}


## Blend States

BlendState BlendState
{
	AlphaTest = no
	WriteMask = "RED|GREEN|BLUE"
	SourceBlend = "src_alpha"
	BlendEnable = yes
	DestBlend = "inv_src_alpha"
}

## Rasterizer States

## Depth Stencil States

## Effects

Effect water
{
	VertexShader = "VertexShader"
	PixelShader = "PixelShader"
}

Effect waterunlit
{
	VertexShader = "VertexShader"
	PixelShader = "PixelShaderUnlit"
}

Effect PdxLake
{
	VertexShader = "VertexShaderLake"
	PixelShader = "PixelShaderLake"
}